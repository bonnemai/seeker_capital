package com.bonnemai.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RestController
@SpringBootApplication
public class Listener implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(Listener.class);

    public static void main(String[] args) {
        SpringApplication.run(Listener.class, args).close();
    }

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private Repository repository;

    private final CountDownLatch latch = new CountDownLatch(3);

    private List<Integer> values=new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            latch.await(60, TimeUnit.SECONDS);
        }
    }

    @KafkaListener(topics = "test")
    public void listen(ConsumerRecord<?, ?> cr) {
        logger.info(String.format("Value: %s, Timestamp %s", cr.value(), cr.timestamp()));
        int intValue=Integer.parseInt(String.valueOf(cr.value()));
        DoubleValue dv=new DoubleValue(intValue, cr.timestamp());
        repository.save(dv);
        values.add(intValue);
        latch.countDown();
    }


    public void setValues(List<Integer> values) {
        this.values = values;
    }

    @RequestMapping(value = "/average", method = RequestMethod.GET)
    public Double average(@RequestParam(value="nb", defaultValue="3") String nb) throws Exception {
        logger.info("Computing the Average of the last {} values", nb);
        Integer nbInt=Integer.parseInt(nb);
        Double sum=0d;

        if (nbInt > 0) {
            if (values.size()>=nbInt) {
                for (int i = values.size() - nbInt; i < values.size(); i++) {
                    sum += values.get(i);
                }
            } else {
                throw new Exception(String.format("Got only %d values in the list. This is not enough. Please come back later or use a smaller nb.",  values.size()));
            }
        } else {
            throw new Exception("nb Should be positive");
        }

        return sum/nbInt;
    }


    /**
     * @return
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        logger.info("Error");
        return "Error";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        logger.info("Index Page");
        return "Hi There";
    }
}

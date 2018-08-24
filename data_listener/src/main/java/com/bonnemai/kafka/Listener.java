package com.bonnemai.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Listener implements CommandLineRunner {

    static Logger logger = LoggerFactory.getLogger(Listener.class);

    public static void main(String[] args) {
        SpringApplication.run(Listener.class, args);
//        SpringApplication.run(Listener.class, args).close();
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
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(String.format("Value: %s, Timestamp %s", cr.value(), cr.timestamp()));
        int intValue=Integer.parseInt(String.valueOf(cr.value()));
        DoubleValue dv=new DoubleValue(intValue, cr.timestamp());
        repository.save(dv);
        values.add(Integer.valueOf(intValue));
        latch.countDown();
    }

    public List<Integer> getValues() {
        return values;
    }
}

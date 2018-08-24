package com.bonnemai.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Producer implements CommandLineRunner {
    static Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        SpringApplication.run(Producer.class, args).close();
    }

    @Autowired
    private KafkaTemplate<String, String> template;

    private final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run(String... args) throws Exception {
        Random rand = new Random();
        while (true) {
            int  n = rand.nextInt(60) ;
            logger.info("Sending & Sleeping: {}",n);
            this.template.send("test", String.valueOf(n));
            latch.await(10*n, TimeUnit.SECONDS);
            Thread.sleep(1000*n);
        }
    }
}

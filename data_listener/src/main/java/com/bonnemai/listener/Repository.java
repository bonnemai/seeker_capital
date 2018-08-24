package com.bonnemai.listener;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Repository extends MongoRepository<DoubleValue, String> {
}

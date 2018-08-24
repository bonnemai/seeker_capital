package com.bonnemai.kafka;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface Repository extends MongoRepository<DoubleValue, String> {
}

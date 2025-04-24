package org.example.xviewer.repository;

import org.example.xviewer.model.EventData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventDataRepository extends MongoRepository<EventData, Long> {
    EventData findByEventData(String eventId);
}

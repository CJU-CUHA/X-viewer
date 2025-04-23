package org.example.xviewer.service;

import org.example.xviewer.model.EventData;
import org.example.xviewer.security.dto.ResMessage;

import java.util.List;

public interface EventDataService {

    ResMessage createEventData(EventData eventData,String eventTime, String eventId);
    EventData findEventData(String eventId);
    List<EventData> findAllEventData(Long caseId);

}

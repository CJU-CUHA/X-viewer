package org.example.xviewer.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.xviewer.model.EventData;
import org.example.xviewer.model.Logs;
import org.example.xviewer.repository.EventCasesRepository;
import org.example.xviewer.repository.EventDataRepository;
import org.example.xviewer.repository.LogsRepository;
import org.example.xviewer.repository.UserRepository;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.service.EventDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventDataServiceImpl implements EventDataService {
    private final EventDataRepository eventDataRepository;
    private final LogsRepository logsRepository;
    private final EventCasesRepository eventCasesRepository;
    @Override
    public ResMessage createEventData(EventData eventData,String eventTime, String eventId) {
        EventData data=eventDataRepository.save(eventData);
        ResMessage resMessage=new ResMessage();

        Logs logs= Logs.builder()
                .eventId(eventId)
                .hash(String.valueOf(data.hashCode()))
                .eventTime(eventTime)
                .build();

        logsRepository.save(logs);
        resMessage.setMessage("Event created");
        return resMessage;
    }

    @Override
    public EventData findEventData(String eventId) {
        return eventDataRepository.findByEventData(eventId);
    }

    @Override
    public List<EventData> findAllEventData(Long caseId) {

        List<Logs> logs=logsRepository.findAllByCaseId(eventCasesRepository.findById(caseId).get());
        List<EventData> eventDataList=new ArrayList<>();
        for (Logs log:logs) {
            eventDataList.add(eventDataRepository.findByEventData(log.getHash()));
        }
        return eventDataList;
    }

}


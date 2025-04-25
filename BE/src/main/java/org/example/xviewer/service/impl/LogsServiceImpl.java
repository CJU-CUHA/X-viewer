package org.example.xviewer.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.xviewer.model.Logs;
import org.example.xviewer.repository.CaseRepository;
import org.example.xviewer.repository.LogsRepository;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.logs.LogsRequest;
import org.example.xviewer.security.dto.logs.LogsResultResponse;
import org.example.xviewer.service.LogsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {

    private final LogsRepository logsRepository;
    private final CaseRepository caseRepository;

    @Override
    public ResMessage createLogs(LogsRequest request) {
        ResMessage resMessage = new ResMessage();
        if(!caseRepository.existsById(request.getCaseId())){
            resMessage.setMessage("Case not found");
            return resMessage;
        }
        Logs logs = Logs.builder()
                .alias(request.getAlias())
                .eventId(request.getEventId())
                .channel(request.getChannel())
                .computer(request.getComputer())
                .eventCasesId(caseRepository.getOne(request.getCaseId()))
                .build();
        logsRepository.save(logs);
        resMessage.setMessage("저장에 성공했습니다.");
        return resMessage;
    }

    @Override
    public LogsResultResponse getLogs(Long Id) {

        return null;
    }

    @Override
    public ResMessage updateLogs(Long id, LogsRequest request) {
        return null;
    }

    @Override
    public ResMessage deleteLogs(Long Id) {
        return null;
    }
}

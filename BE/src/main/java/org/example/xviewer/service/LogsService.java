package org.example.xviewer.service;

import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.logs.LogsRequest;
import org.example.xviewer.security.dto.logs.LogsResultResponse;

public interface LogsService {
    ResMessage createLogs(LogsRequest request);
    LogsResultResponse getLogs(Long Id);
    ResMessage updateLogs(Long id,LogsRequest request);
    ResMessage deleteLogs(Long Id);
}

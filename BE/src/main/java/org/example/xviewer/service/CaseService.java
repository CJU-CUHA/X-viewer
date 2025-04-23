package org.example.xviewer.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.logcase.CaseRequest;
import org.example.xviewer.security.dto.logcase.CaseResultResponse;
import org.springframework.data.domain.Page;

public interface CaseService {
    ResMessage createCase(CaseRequest caseRequest, HttpServletRequest request);
    Page<CaseResultResponse> getListCase(HttpServletRequest request);
    CaseResultResponse getCase(Long id);
    ResMessage updateCase(Long id,CaseRequest caseRequest);
    ResMessage deleteCase(Long id);
}

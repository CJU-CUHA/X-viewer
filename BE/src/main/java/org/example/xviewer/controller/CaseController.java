package org.example.xviewer.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.xviewer.model.EventData;
import org.example.xviewer.repository.CaseRepository;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.logcase.CaseRequest;
import org.example.xviewer.security.dto.logcase.CaseResultResponse;
import org.example.xviewer.service.CaseService;
import org.example.xviewer.service.EventDataService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/case")
@RequiredArgsConstructor
public class CaseController {
    private final CaseService caseService;
    private final EventDataService eventDataService;

    @PostMapping("/create")
    public ResponseEntity<ResMessage> createCase(CaseRequest caseRequest, HttpServletRequest request) {
        ResMessage resMessage=caseService.createCase(caseRequest, request);

        return ResponseEntity.ok(resMessage);
    }

    @GetMapping("/eventdata")
    public ResponseEntity<List<EventData>> getCase(@RequestParam Long id) {
        return ResponseEntity.ok(eventDataService.findAllEventData(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<CaseResultResponse>> getCases(HttpServletRequest request) {
        return ResponseEntity.ok(caseService.getListCase(request));
    }
    @PutMapping("/list")
    public ResponseEntity<ResMessage> updateCase(@RequestParam Long id) {
        return null;
    }

    @DeleteMapping("/list")
    public ResponseEntity<ResMessage> deleteCase(@RequestParam Long id) {
        return null;
    }
}

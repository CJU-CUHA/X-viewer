package org.example.xviewer.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.xviewer.model.EventCases;
import org.example.xviewer.model.User;
import org.example.xviewer.repository.CaseRepository;
import org.example.xviewer.repository.UserRepository;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.logcase.CaseRequest;
import org.example.xviewer.security.dto.logcase.CaseResultResponse;
import org.example.xviewer.security.jwt.JWTUtil;
import org.example.xviewer.service.CaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    @Override
    public ResMessage createCase(CaseRequest caseRequest,HttpServletRequest request) {
        ResMessage resMessage = new ResMessage();
        String username=jwtUtil.getUsername(request);
        if(!userRepository.findByUsername(username).isPresent()){
            resMessage.setMessage("404Not Found");
            return resMessage;
        }

        EventCases saveEventCases = EventCases.builder()
                .caseInfo(caseRequest.getCaseInfo())
                .caseName(caseRequest.getCaseName())
                .caseType(caseRequest.getCaseType())
                .caseOwner(userRepository.findByUsername(username).get()).build();
        caseRepository.save(saveEventCases);
        resMessage.setMessage("저장되었습니다.");
        return resMessage;
    }

    @Override
    public Page<CaseResultResponse> getListCase(HttpServletRequest request) {
        String username=jwtUtil.getUsername(request);
        if(!userRepository.findByUsername(username).isPresent()){
           throw new UsernameNotFoundException("404Not Found");
        }
        Pageable pageable = PageRequest.of(0, 10);
        Page<EventCases> casePage=caseRepository.findAllByCaseOwner_Id(userRepository.findByUsername(username).get().getId(),pageable);

        // Case -> CaseDto 변환
        return casePage.map(CaseResultResponse::fromEntity);
    }

    @Override
    public CaseResultResponse getCase(Long id) {
        Optional<EventCases> findCase = caseRepository.findById(id);
        if(!findCase.isPresent()){
            throw new UsernameNotFoundException("404Not Found");
        }
        EventCases result = caseRepository.findById(id).get();
        CaseResultResponse caseResponse = CaseResultResponse.builder()
                .caseInfo(result.getCaseInfo())
                .caseName(result.getCaseName())
                .caseType(result.getCaseType())
                .caseOwner(result.getCaseOwner().getUsername()).build();
        return caseResponse;
    }

    @Override
    public ResMessage updateCase(Long id,CaseRequest caseRequest) {
        Optional<EventCases> case1=caseRepository.findById(id);
        ResMessage resMessage = new ResMessage();
        Optional<User> user = userRepository.findByUsername(caseRequest.getCaseOwner());
        if(!case1.isPresent() || !user.isPresent()){
            resMessage.setMessage("404Not Found");
            return resMessage;
        }
        EventCases findEventCases = EventCases.builder()
                .caseOwner(user.get())
                .caseType(caseRequest.getCaseType())
                .caseInfo(caseRequest.getCaseInfo())
                .caseName(caseRequest.getCaseName()).build();
        caseRepository.save(findEventCases);
        resMessage.setMessage("update success");
        return resMessage;
    }

    @Override
    public ResMessage deleteCase(Long id) {
        return null;
    }
}

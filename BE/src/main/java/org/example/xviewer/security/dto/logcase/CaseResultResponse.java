package org.example.xviewer.security.dto.logcase;

import lombok.*;
import org.example.xviewer.model.EventCases;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class CaseResultResponse {

    private String caseName;
    private String caseInfo;
    private String caseType;
    private String caseOwner;
    private LocalDateTime createdAt;
    // 엔티티를 DTO로 변환하는 정적 메서드 추가
    public static CaseResultResponse fromEntity(EventCases eventCasesEntity) {
        return new CaseResultResponse(
                eventCasesEntity.getCaseName(),
                eventCasesEntity.getCaseInfo(),
                eventCasesEntity.getCaseType(),
                eventCasesEntity.getCaseOwner().getUsername(),
                eventCasesEntity.getCreated()
        );
    }
}

package org.example.xviewer.security.dto.logcase;

import lombok.*;
import org.example.xviewer.model.Case;

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
    public static CaseResultResponse fromEntity(Case caseEntity) {
        return new CaseResultResponse(
                caseEntity.getCaseName(),
                caseEntity.getCaseInfo(),
                caseEntity.getCaseType(),
                caseEntity.getCaseOwner().getUsername(),
                caseEntity.getCreated()
        );
    }
}

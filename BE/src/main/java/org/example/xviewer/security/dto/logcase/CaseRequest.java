package org.example.xviewer.security.dto.logcase;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class CaseRequest {
    private String caseName;
    private String caseInfo;
    private String caseType;
    private String caseOwner;
}

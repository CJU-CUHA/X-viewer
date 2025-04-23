package org.example.xviewer.security.dto.logs;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class LogsResultResponse {
    private Long caseId;
    private String alias;
    private Long eventId;
    private String channel;
    private String computer;

}

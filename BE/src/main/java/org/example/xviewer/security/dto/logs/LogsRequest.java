package org.example.xviewer.security.dto.logs;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LogsRequest {

    private Long caseId;
    private String alias;
    private String eventId;
    private String channel;
    private String computer;


}

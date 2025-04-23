package org.example.xviewer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Logs extends TimeBaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private Case caseId;
    private String alias;
    @Column(name = "event_id")
    private String eventId;
    private String eventTime;
    private String channel;
    private String computer;
    private String hash;
}

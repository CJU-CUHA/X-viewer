package org.example.xviewer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_cases")
public class Case extends TimeBaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "case_name")
    private String caseName;
    @Column(name = "case_info")
    private String caseInfo;
    @Column(name = "case_type")
    private String caseType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK 명시
    private User caseOwner;
}

package org.example.xviewer.repository;

import org.example.xviewer.model.Case;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
    Page<Case> findAllByCaseOwner_Id(Long caseOwnerId,Pageable pageable);
}

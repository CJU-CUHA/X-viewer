package org.example.xviewer.repository;

import org.example.xviewer.model.EventCases;
import org.example.xviewer.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogsRepository extends JpaRepository<Logs, Long> {
    List<Logs> findAllByEventCasesId_Id(Long caseId);
}

package org.setana.treenity.repository;

import java.time.LocalDate;
import java.util.List;
import org.setana.treenity.entity.WalkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkLogRepository extends JpaRepository<WalkLog, Long>, WalkLogRepositoryCustom {

    List<WalkLog> findByUser_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}

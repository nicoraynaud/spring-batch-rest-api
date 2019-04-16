package com.github.nicoraynaud.batch.repository;

import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobExecutionEntityRepository extends JpaRepository<JobExecutionEntity, Long> {

    Optional<JobExecutionEntity> findById(Long id);

    Page<JobExecutionEntity> findAllByStatusIsIn(Pageable pageable, List<String> status);

    @Query("select e from JobExecutionEntity e where e.jobInstance.jobName = :name and (:status is null or e.status = :status) and (cast(:beginStartTime as date)  is null or e.startTime > :beginStartTime) and (cast(:endStartTime as date)  is null or e.startTime < :endStartTime)")
    Page<JobExecutionEntity> findAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore(Pageable pageable, @Param("name") String name, @Param("status") String status, @Param("beginStartTime") LocalDateTime beginStartTime, @Param("endStartTime") LocalDateTime endStartTime);

    @Query("select e from JobExecutionEntity e where e.id in(select max(e2.id) from JobExecutionEntity e2 join e2.jobInstance i2 where i2.jobName = :jobName)")
    JobExecutionEntity getLastJobExecution(@Param("jobName") String jobName);

    int deleteByCreateTimeBeforeAndIdNot(LocalDateTime dateSuppression, Long idJobEnCours);
}

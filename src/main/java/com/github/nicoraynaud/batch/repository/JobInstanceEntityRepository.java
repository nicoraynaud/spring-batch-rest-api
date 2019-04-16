package com.github.nicoraynaud.batch.repository;

import com.github.nicoraynaud.batch.domain.entity.JobInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobInstanceEntityRepository extends JpaRepository<JobInstanceEntity, Long> {

    @Modifying
    @Query("delete from JobInstanceEntity e where e.id not in (select e2.jobInstance.id from JobExecutionEntity e2)")
    int deleteAllByIdNotInJobExecutionEntity();
}

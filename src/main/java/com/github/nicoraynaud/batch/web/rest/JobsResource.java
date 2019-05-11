package com.github.nicoraynaud.batch.web.rest;

import com.github.nicoraynaud.batch.dto.JobDescriptionDTO;
import com.github.nicoraynaud.batch.dto.JobExecutionDTO;
import com.github.nicoraynaud.batch.dto.JobExecutionParameterInputDTO;
import com.github.nicoraynaud.batch.dto.StepExecutionDTO;
import com.github.nicoraynaud.batch.exception.JobBadArgumentException;
import com.github.nicoraynaud.batch.service.rest.JobRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping({"/management/jobs"})
public class JobsResource {
    private final Logger log = LoggerFactory.getLogger(JobsResource.class);

    private final JobRestService jobRestService;

    public JobsResource(JobRestService jobRestService) {
        this.jobRestService = jobRestService;
    }

    @GetMapping
    public ResponseEntity<List<JobDescriptionDTO>> getJobs(@RequestParam(name = "jobName", required = false) String jobName) {
        log.debug("REST request to getJobs");
        return ResponseEntity.ok(jobRestService.getJobs(jobName));
    }

    @GetMapping("/{name}")
    public ResponseEntity<JobDescriptionDTO> getJob(@PathVariable("name") String name) {
        log.debug("REST request to getJob : {}", name);
        try {
            return ResponseEntity.ok(jobRestService.getJob(name));
        } catch (JobBadArgumentException e) {
            log.error("REST request to getJob, bad argument : {}", name, e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{jobName}/start")
    public ResponseEntity<JobExecutionDTO> start(@PathVariable String jobName, @RequestBody List<JobExecutionParameterInputDTO> jobParameters) {
        log.debug("REST request to start : {}", jobName);
        return ResponseEntity.ok(jobRestService.start(jobName, jobParameters));
    }

    @GetMapping(value = "/{jobName}/executions")
    public ResponseEntity<Page<JobExecutionDTO>> getJobNameExecutions(@PathVariable String jobName, Pageable pageable,
                                                                      @RequestParam(name = "status", required = false) String status,
                                                                      @RequestParam(name = "executionBeginDate", required = false) LocalDateTime executionBeginDate,
                                                                      @RequestParam(name = "executionEndDate", required = false) LocalDateTime executionEndDate) {
        log.debug("REST request to start : {}", jobName);
        return ResponseEntity.ok(jobRestService.getJobNameExecutions(pageable, jobName, status, executionBeginDate, executionEndDate));
    }

    @GetMapping("/executions")
    public ResponseEntity<Page<JobExecutionDTO>> getJobExecutions(Pageable pageable, @RequestParam(name = "status", required = false) String[] statuses) {
        log.debug("REST request to getJobExecutions");
        return ResponseEntity.ok(jobRestService.getJobExecutions(pageable, statuses));
    }

    @GetMapping("/executions/{id}")
    public ResponseEntity<JobExecutionDTO> getJobExecution(@PathVariable("id") Long id) {
        log.debug("REST request to getJobExecution {}", id);
        return ResponseEntity.ok(jobRestService.getJobExecution(id));
    }

    @PostMapping("/executions/{id}/stop")
    public ResponseEntity<Boolean> stop(@PathVariable("id") Long id) {
        log.debug("REST request to stop {}", id);

        try {
            return ResponseEntity.ok(jobRestService.stop(id));
        } catch (JobBadArgumentException e) {
            log.error("REST request to stop bad argument : {}", id, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/executions/{id}/stepexecutions")
    public ResponseEntity<List<StepExecutionDTO>> getJobExecutionStepExecutions(@PathVariable("id") Long id) {
        log.debug("REST request to getJobExecutionStepExecutions {}", id);
        try {
            return ResponseEntity.ok(jobRestService.getJobExecutionStepExecutions(id));
        } catch (JobBadArgumentException e) {
            log.error("REST request to getJobExecutionStepExecutions, bad argument : {}", id, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

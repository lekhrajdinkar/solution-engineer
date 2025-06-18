package com.lekhraj.java.spring.etl.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchService {

    @Autowired
    private JobLauncher jobLauncher;

    @Async("taskExecutor")
    public void runJob(Job job, JobParameters params)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException
    {
        System.out.println("Task executed by thread: " + Thread.currentThread().getName());
        JobExecution jobExecution = jobLauncher.run(job, params);
        log.info(job.getName() + " status - " + jobExecution.getStatus());
    }
}

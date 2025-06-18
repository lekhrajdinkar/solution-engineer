package com.lekhraj.java.spring.etl.springbatch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {


    @Autowired    ApplicationContext ac ;
    @Autowired BatchService batchSrv;

    /**
     * job_1_importUser,
     * job_2_processOrder
     */
    @GetMapping("/run-job/{jobBeanName}")
    public ResponseEntity<String> runJob(@PathVariable("jobBeanName") String jobBeanName)
    {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            Job job = ac.getBean(jobBeanName, Job.class);
            batchSrv.runJob(job, params);
            return ResponseEntity.ok("Job " + jobBeanName + " initiated..." );
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to start job: " + e.getMessage());
        }
    }
}


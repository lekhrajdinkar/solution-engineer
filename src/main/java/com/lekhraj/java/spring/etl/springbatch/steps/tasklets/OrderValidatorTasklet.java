package com.lekhraj.java.spring.etl.springbatch.steps.tasklets;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderValidatorTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            org.springframework.batch.core.scope.context.ChunkContext chunkContext)
    {
        System.out.println("Validating processed orders...");
        return RepeatStatus.FINISHED;
    }
}


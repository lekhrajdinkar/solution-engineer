package com.lekhraj.java.spring.etl.springbatch;


import com.lekhraj.java.spring.etl.springbatch.entity.Order;
import com.lekhraj.java.spring.etl.springbatch.listeners.job.CommonJobListener;
import com.lekhraj.java.spring.etl.springbatch.entity.User;
import com.lekhraj.java.spring.etl.springbatch.steps.Reader.OrderItemReader;
import com.lekhraj.java.spring.etl.springbatch.steps.processer.OrderProcessor;
import com.lekhraj.java.spring.etl.springbatch.steps.processer.OrderWriter;
import com.lekhraj.java.spring.etl.springbatch.steps.processer.UserItemProcessor;
import com.lekhraj.java.spring.etl.springbatch.steps.tasklets.OrderValidatorTasklet;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;

import javax.sql.DataSource;

@Configuration

@EnableBatchProcessing(
        dataSourceRef = "dataSource_for_postgres2",
        transactionManagerRef = "transactionManager_for_postgres2"
)
public class BatchConfig
{
    @Autowired JobRepository jobRepository;
    @Autowired CommonJobListener listener;

    @Autowired @Qualifier("dataSource_for_postgres2")    DataSource ds_for_postgres;
    @Autowired @Qualifier("transactionManager_for_postgres2")    PlatformTransactionManager transactionManager;

    /*@Bean
    public JobRepository jobRepository() throws Exception
    {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(ds_for_postgres);
        factoryBean.setTransactionManager(transactionManager);

        factoryBean.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ"); // Optional
        factoryBean.setTablePrefix("BATCH_"); // Optional: Prefix for batch metadata tables
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }*/

    //==== Job-1 ====

    @Bean("job_1_importUser")
    public Job importUserJob(
            @Qualifier("step_1_user") Step step
    )
    {
        return new JobBuilder("job_1_importUser", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .listener(listener)
                .build();
    }

    @Bean("step_1_user")
    public Step step(
            @Qualifier("user_csv_reader") FlatFileItemReader<User> reader,
            @Qualifier("user_processor_1_uppercase") UserItemProcessor processor,
            @Qualifier("user_db_writer") JdbcBatchItemWriter<User> writer
    )
    {
        return new StepBuilder("step_1_user", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    //==== Job-2 ====

    @Bean("job_2_processOrder")
    public Job processOrderJob(
            @Qualifier("processOrderStep")  Step processOrderStep,
            @Qualifier("validateOrderStep") Step validateOrderStep
    )
    {
        return new JobBuilder("job_2_processOrder", jobRepository)
                .start(processOrderStep)
                .next(validateOrderStep)
                .build();
    }

    @Bean
    public Step processOrderStep(
            OrderItemReader reader,
            OrderProcessor processor,
            OrderWriter writer)
    {
        return new StepBuilder("processOrderStep", jobRepository)
                .<Order, Order>chunk(20, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step validateOrderStep( OrderValidatorTasklet tasklet)
    {
        return new StepBuilder("validateOrderStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
}


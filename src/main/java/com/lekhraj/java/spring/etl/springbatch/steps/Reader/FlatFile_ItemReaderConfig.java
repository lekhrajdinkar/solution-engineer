package com.lekhraj.java.spring.etl.springbatch.steps.Reader;

import com.lekhraj.java.spring.etl.springbatch.entity.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FlatFile_ItemReaderConfig
{
    @Bean("user_csv_reader")
    @StepScope
    public FlatFileItemReader<User> reader()
    {
        return new FlatFileItemReaderBuilder<User>()
                .name("userItemReader")
                .resource(new ClassPathResource("users.csv"))
                .delimited()
                .delimiter(",")
                .names("id","firstName", "lastName", "email")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(User.class);
                }})
                .linesToSkip(1)
                .build();
    }
}

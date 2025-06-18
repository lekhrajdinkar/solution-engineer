package com.lekhraj.java.spring.etl.springbatch.steps.Writer;

import com.lekhraj.java.spring.etl.springbatch.entity.User;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DB_ItemWriter {

    @Bean("user_db_writer")
    public org.springframework.batch.item.database.JdbcBatchItemWriter<User> writer(@Qualifier("dataSource_for_postgres2") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<User>()
                .dataSource(dataSource)
                .sql("INSERT INTO app_user (firstname, lastname, email) VALUES (:firstName, :lastName, :email)")
                .beanMapped()
                .build();
    }
}

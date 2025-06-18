https://chatgpt.com/c/6752bc19-2988-800d-a0b6-b2d118c74141
---
# Spring batch
- Architecture
- configure a jobRepository
- Job parameter
- Building job with multiple Steps

## Job flow
- status control
-  job flow
  - conditional Flow
  - parallel flow
- Listeners
- StepExecutionListener
- Restarting jobs
- Reusability - reuse same step among several job.
- nested jobs
```


job1
    .listener(listener)                                     # event - job start,end
    .next(step2())
    .next(job2Reference())                                          # nested
    .split(new SimpleAsyncTaskExecutor()).add(step3(), step4())     # parallel

Step1.
    .<InputType, OutputType>chunk(10)
    .listener(listener)                                     # event - step start,end

```

## Reading
- chuck-oriented processing
- ItemReader
- configure chuck-oriented steps
- reading from DB (single thread)
- reading from DB (multiple thread)
```
 return new JdbcCursorItemReader<>()
```

## Writing
- ItemWriter 
- Writing flat files 
- Writing to a database with PreparedStatements 
- Writing to a database with named parameters 
- Writing a JSON file 
```
 return new JdbcBatchItemWriter<>();
 FlatFileItemWriter<OutputType> writer = new FlatFileItemWriter<>();
```

## processing Items
- ItemProcessor<InputType, OutputType> 
- ItemProcessor Bean Validation
- Implementing custom processor logic
- Chaining ItemProcessors
- Filtering batch data

## Resilient jobs
- skip logic for jobs
- retry logic for steps
- multithreaded steps
```

Step1.
    .skip(Exception.class)
    .skipLimit(10)
    
Step1.
    .retry(Exception.class)
    .retryLimit(3) 
    
Step1.    
    .taskExecutor(new SimpleAsyncTaskExecutor())  
```



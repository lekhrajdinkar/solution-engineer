# RMQ
## Environment Setup
```bash
docker run -d --hostname my-rabbit --name my-rabbit -p 5672:5672 -p 15672:15672  rabbitmq:3-management -e ssl_options.verify:verify_none rabbitmq:latest

# UI console : guest/guest http://localhost:15672/
````
---
## Outline of springboot project
```java
    // ============ Connectionfactory ========
    
    @Bean
    public ConnectionFactory rmqConnectionfactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setUri("amqps://localhost:5672");
        factory.setVirtualHost("/");
        factory;
    }
    
    // ============ RabbitTemplate ========
    
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
    
    // ============ Exchanage,queue and binding ========
    
    @Bean
    Queue queue() { 
    return  QueueBuilder.durable(queueName)
                        .quorum()               # quorum
                        .build();
    }
    
    @Bean
    DirectExchange exchange() {
        return ExchangeBuilder
        .directExchange(exchangeName)           # direct
        .build();
        }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(key);
    }
    
    // =============== Receive ==========
    
    @RabbitListener(queues="spring.app.queue1")
    public void receiveMessage(String message) {
        System.out.println("Rabbit MQ test Received <" + message + ">");
    }


    // =============== Send ==========

    @Bean
    CommandLineRunner rabbitMQtest(RabbitTemplate rabbitTemplate){
        return (args)->{
            rabbitTemplate.convertAndSend(exchangeName, key, "{message}", m->m);
            System.out.println("Rabbit MQ test message sent at startup");
        };
    }

    // =============== MessageConverter ==========

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
```
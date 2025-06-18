# connect to cluster - cluster.config
security.protocol=SASL_SSL
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="<your username>" password="<your password>";
sasl.mechanism=PLAIN
# disable auto topic creation here.
# if enable, then default :: partition=1 and RF=1

######################## A CREATE TOPIC ##################
# Start running commands
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic first_topic
## --partitions 1 (default)

kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic second_topic \
--partitions 3

kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic third_topic  \
--partitions 5 \
--replication-factor 2

# list topics
kafka-topics --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --list

# describe topics
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --topic first_topic --describe

# delete topics
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --topic first_topic --delete
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --topic second_topic --delete
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --topic third_topic --delete

######################## B PRODUCE ##################


kafka-console-producer.sh --producer.config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 \
--topic first_topic \
--producer-property acks=all

> aaaaaaaaaa
> bbbbbbbbbbbbb
> ccccccccc
>^C  (<- Ctrl + C is used to exit the producer)


# produce with keys
kafka-console-producer.sh \
--producer.config playground.config \
--bootstrap-server cluster.playground.cdkt.io:9092 \
--topic first_topic \
--property parse.key=true \
--property key.separator=: \
>example key:example value

> key1:aaaaaaaaaa
> key2:bbbbbbbbbbbbb
> key2:ccccccccc
>^C  (<- Ctrl + C is used to exit the producer)

######################## C1 CONSUMER  ##################

# consume topic one - 1 partition : ordered
kafka-console-producer.sh --producer.config playground.config --bootstrap-server cluster.playground.cdkt.io:9092  \
--producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner \
--topic first_topic


# consume topic second - 3 partition : ordered in each partition
kafka-console-consumer.sh --consumer.config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 \
--topic second_topic \
--from-beginning \
--formatter kafka.tools.DefaultMessageFormatter  \
# enable below columns
--property print.timestamp=true  \
--property print.key=true  \
--property print.value=true   \
--property print.partition=true

######################## C2 CONSUMER Group  ##################
# describe group and then consume

# 1. describe group/s
kafka-consumer-groups.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --describe --group my-first-application --reset-offsets --to-earliest
kafka-consumer-groups.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --describe --group my-second-application
kafka-consumer-groups.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --describe --group console-consumer-10592

# 2. consume
# ----- group-1 -------------start
# start a consumer-1
kafka-console-consumer.sh --consumer.config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 \
--topic first_topic \
--group my-first-application

# start a consumer-2
kafka-console-consumer.sh --consumer.config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 \
--topic first_topic \
--group my-first-application
# ----- group-1 -------------end


# ----- group-2 -------------start
kafka-console-consumer.sh --consumer.config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 \
--topic first_topic \
--group console-consumer-10592
# ----- group-2 -------------end




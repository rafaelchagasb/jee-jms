# JMS 

Example to send a message in JMS

## How do I run?

1 Compile project

```mvn package```

2 Configure your standalone-full (wildfly/jboss)

```xml
<subsystem xmlns="urn:jboss:domain:resource-adapters:5.0">
    <resource-adapters>
        <resource-adapter id="wmq.jmsra">
            <archive>
                wmq.jmsra.rar
            </archive>
            <transaction-support>NoTransaction</transaction-support>
            <connection-definitions>
                <connection-definition class-name="com.ibm.mq.connector.outbound.ManagedConnectionFactoryImpl" jndi-name="java:jboss/jms/QueueConnectionFactory" enabled="true" use-java-context="true" pool-name="QueueConnectionFactory">
                    <config-property name="hostName">
                        localhost
                    </config-property>
                    <config-property name="password">
                        passw0rd
                    </config-property>
                    <config-property name="queueManager">
                        QM1
                    </config-property>
                    <config-property name="port">
                        1414
                    </config-property>
                    <config-property name="channel">
                        DEV.APP.SVRCONN
                    </config-property>
                    <config-property name="transportType">
                        CLIENT
                    </config-property>
                    <config-property name="username">
                        app
                    </config-property>
                </connection-definition>
            </connection-definitions>
            <admin-objects>
                <admin-object class-name="com.ibm.mq.connector.outbound.MQQueueProxy" jndi-name="java:jboss/jms/DEV.QUEUE.1" enabled="true" pool-name="DEV.QUEUE.1">
                    <config-property name="baseQueueName">
                        DEV.QUEUE.1
                    </config-property>
                    <config-property name="CCSID">
                        500
                    </config-property>
                </admin-object>
            </admin-objects>
        </resource-adapter>
    </resource-adapters>
</subsystem>
```

3 Run IBM MQ local using Docker

```shell
docker volume create qm1data
docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest
```

4 IBM Console Administration

https://localhost:9443/ibmmq/console/#/

- user: admin
- password: passw0rd

5 Deploy war and run Wildfly/Jboss


## How do I test?

Send a request

```shell
curl --location --request GET 'http://localhost:8080/jee-jms/api/messages/mycontent'
```
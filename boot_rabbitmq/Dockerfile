FROM java:8
VOLUME ["/tmp","/data0001"]
COPY boot_rabbitmq-0.0.1-SNAPSHOT.jar /rabbitmq-1.0.0.jar
RUN bash -c "touch /rabbitmq-1.0.0.jar"
EXPOSE 8021
ENTRYPOINT ["java","-jar","rabbitmq-1.0.0.jar"]
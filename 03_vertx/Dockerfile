FROM openjdk:8u102-jdk

ENV VERTICLE_FILE 03_vertx-1.0.0-SNAPSHOT-fat.jar
ENV CONFIG_FILE inventory-conf.json
# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8282

COPY $VERTICLE_FILE $VERTICLE_HOME/
COPY $CONFIG_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME

ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $VERTICLE_FILE -conf $CONFIG_FILE"]
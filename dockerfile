FROM openjdk:17-slim

ENV LANGUAGE='en_US:en'

WORKDIR /deployments

COPY --chown=185 build/libs/* /deployments/

EXPOSE 8080

USER 185

ENV JAVA_OPTS_APPEND="-Dserver.address=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/tm-1.0-SNAPSHOT.jar"

ENTRYPOINT ["sh", "-c", "java -jar ${JAVA_APP_JAR}"]


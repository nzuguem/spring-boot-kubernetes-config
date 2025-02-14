FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

RUN addgroup --gid 1001 app-user &&\
    useradd --uid 1001 --gid 1001 -m app-user

ENV JAVA_OPTS="-Djava.security.edg=file:/dev/./urandom"
COPY --chown=app-user target/kubernetes-config.jar app.jar

EXPOSE 8080

USER app-user

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
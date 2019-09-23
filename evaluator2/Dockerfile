FROM openjdk:8-jre

MAINTAINER David Mejia <davidndy@gmail.com>

#VOLUME /tmp
#ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/app/app.jar"]
# Add Maven dependencies (not shaded into the artifact; Docker-cached)
#ADD target/lib           /usr/share/app/lib
# Add the service itself
#ARG JAR_FILE
#ADD target/${JAR_FILE} /usr/share/app/app.jar
#ARG JAR_FILE

COPY . /codeEvaluator

EXPOSE 8080

RUN  ls
RUN  ls codeEvaluator/

ENTRYPOINT ["java","-jar","codeEvaluator/target/evaluator2-1.0.0.jar"]

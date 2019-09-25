FROM openjdk:8-jre

MAINTAINER David Mejia <davidndy@gmail.com>

COPY . /codeEvaluator

EXPOSE 8080

#java -jar codeEvaluator/target/evaluator2-1.0.0.jar

ENTRYPOINT ["java","-jar","codeEvaluator/target/evaluator2-1.0.0.jar"]
#ENTRYPOINT ["bin/bash"]

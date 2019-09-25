FROM openjdk:8-jre

MAINTAINER David Mejia <davidndy@gmail.com>
VOLUME /david

RUN  ls
RUN touch /david/testFile3.txt
RUN  ls david 

COPY . /codeEvaluator

EXPOSE 8080

RUN  ls
RUN  ls codeEvaluator/
RUN  ls david
#ENTRYPOINT ["java","-jar","codeEvaluator/target/evaluator2-1.0.0.jar"]
#java -jar codeEvaluator/target/evaluator2-1.0.0.jar

package evaluatorSpringBoot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import evaluatorSpringBoot.persistance.dao.ResponseDao;
import evaluatorSpringBoot.persistance.dao.ResponsePostgresDaoImpl;
import evaluatorSpringBoot.persistance.dao.SubmissionDao;
import evaluatorSpringBoot.persistance.dao.SubmissionPostgresDaoImpl;

@Configuration
//@ComponentScan("evaluatorSpringBoot")
public class Config {

    @Bean
    public SubmissionDao submissionDao() {
        return new SubmissionPostgresDaoImpl();
    }

    @Bean
    public ResponseDao responseDao() {
        return new ResponsePostgresDaoImpl();
    }
}
/*
package com.baeldung.constructordi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.constructordi.domain.Engine;
import com.baeldung.constructordi.domain.Transmission;

@Configuration
@ComponentScan("com.baeldung.constructordi")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("v8", 5);
    }

    @Bean
    public Transmission transmission() {
        return new Transmission("sliding");
    }
}*/
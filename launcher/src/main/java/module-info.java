module launcher {
    requires spring.context;
    requires static lombok;
    requires spring.data.mongodb;
    requires spring.beans;
    requires reactor.core;
    requires spring.boot;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.reactivestreams;
    requires io.swagger.v3.oas.models;
    requires spring.webflux;
    requires spring.web;
    requires jakarta.validation;
    requires io.vavr;
    requires io.swagger.v3.oas.annotations;
    requires events;
    requires org.slf4j;
    requires org.springdoc.openapi.common;
}
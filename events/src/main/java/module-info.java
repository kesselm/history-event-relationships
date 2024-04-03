module events {
    exports de.kessel.events.service to launcher;
    exports de.kessel.events.dto to launcher;
    exports de.kessel.events.exception to launcher;
    requires static lombok;
    requires org.asciidoctor.asciidoctorj.api;
    requires reactor.core;
    requires spring.context;
    requires spring.data.mongodb;
    requires spring.data.commons;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.validation;
    requires spring.web;
    requires spring.beans;
    requires org.mongodb.driver.reactivestreams;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.core;
    requires spring.webflux;
    requires io.vavr;
    requires org.slf4j;
    requires org.apache.commons.lang3;
}
module events {
    exports de.kessel.events.service to launcher;
    exports de.kessel.events.dto to launcher;
    exports de.kessel.events.exception to launcher;
    opens de.kessel.events to spring.core, spring.beans, spring.context;
    opens de.kessel.events.config to spring.core, spring.beans, spring.context;
    opens de.kessel.events.model to spring.core, spring.beans, spring.context;
    opens de.kessel.events.service.implementation to spring.core, spring.beans, spring.context;
    opens de.kessel.events.exception to spring.beans, spring.context;
    requires static lombok;
    requires org.asciidoctor.asciidoctorj.api;
    requires reactor.core;
    requires spring.context;
    requires spring.data.mongodb;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.reactivestreams;
    requires spring.data.commons;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.validation;
    requires spring.web;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.core;
    requires spring.webflux;
    requires io.vavr;
    requires org.slf4j;
    requires org.apache.commons.lang3;
}
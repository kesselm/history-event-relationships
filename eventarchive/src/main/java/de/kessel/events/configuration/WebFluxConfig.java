package eventarchieve.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer
{

//    @Bean
//    public WebExceptionHandler exceptionHandler() {
//        return (ServerWebExchange exchange, Throwable ex) -> {
//            if (ex instanceof CustomErrorException) {
//                exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
//                return exchange.getResponse().setComplete();
//            }
//            return Mono.error(ex);
//        };
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("/swagger-ui.html**")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}
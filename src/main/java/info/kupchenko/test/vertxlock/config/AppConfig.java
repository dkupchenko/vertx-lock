package info.kupchenko.test.vertxlock.config;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.shareddata.SharedData;
import io.vertx.reactivex.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс AppConfig
 *
 * @author dkupchenko
 * created 15.09.2020
 */
@Configuration
public class AppConfig {

    @Bean
    Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    Router router(Vertx vertx) {
        return Router.router(vertx);
    }

    @Bean
    SharedData sharedData(Vertx vertx) {
        return vertx.sharedData();
    }

}

package info.kupchenko.test.vertxlock.verticle;

import info.kupchenko.test.vertxlock.controller.IRestController;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.LoggerHandler;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Класс HttpVerticle
 *
 * @author dkupchenko
 * created 15.09.2020
 */
@Slf4j
@Setter
@Component
@RequiredArgsConstructor
@ConfigurationProperties("app.http")
public class HttpVerticle extends AbstractVerticle {

    // beans
    final Vertx vertx;
    final Router router;
    final List<IRestController> controllers;
    //props
    int port;


    @PostConstruct
    void info() {
        log.info("port=[{}]", port);
    }

    @Override
    public void start() throws Exception {
        super.start();

        // Access log
        //router.route().handler(LoggerHandler.create(LoggerFormat.SHORT));

        for(IRestController controller: controllers) {
            controller.bind(router);
        }

        vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(port)
                .subscribe(
                        server -> log.debug("HTTP-Сервер запущен. Порт: {}", server.actualPort()),
                        error -> log.error("Ошибка запуска HTTP-сервера", error));

    }
}

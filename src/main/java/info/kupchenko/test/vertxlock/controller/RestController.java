package info.kupchenko.test.vertxlock.controller;

import info.kupchenko.test.vertxlock.service.CounterService;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Класс RestController
 *
 * @author dkupchenko
 * created 15.09.2020
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RestController implements IRestController {

    // beans
    final CounterService service;


    @PostConstruct
    void info() {
        log.info("dec=[{}], hex=[{}]", service.getCounter(), service.getHexCounter());
    }

    @Override
    public void bind(Router router) {
        log.info("bind(): GET / -> getCounter()");
        router.get("/").handler(this::getCounter);
        log.info("bind(): GET /hex -> getHexCounter()");
        router.get("/hex").handler(this::getHexCounter);
    }

    void getCounter(RoutingContext routingContext) {
        var result = service.getCounter();
        log.debug("getCounter() = [{}]", result);
        if(result != null) {
            routingContext
                    .response()
                    .putHeader("Content-Type", "text/plain")
                    .end(result);
        } else {
            routingContext
                    .response()
                    .setStatusCode(404)
                    .end();
        }
    }

    void getHexCounter(RoutingContext routingContext) {
        var result = service.getHexCounter();
        log.debug("getHexCounter() = [{}]", result);
        if(result != null) {
            routingContext
                    .response()
                    .putHeader("Content-Type", "text/plain")
                    .end(result);
        } else {
            routingContext
                    .response()
                    .setStatusCode(404)
                    .end();
        }
    }

}

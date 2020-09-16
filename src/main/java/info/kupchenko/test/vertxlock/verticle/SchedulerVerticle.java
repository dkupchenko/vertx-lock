package info.kupchenko.test.vertxlock.verticle;

import info.kupchenko.test.vertxlock.service.CounterService;
import io.vertx.reactivex.core.AbstractVerticle;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Класс SchedulerVerticle
 *
 * @author dkupchenko
 * created 15.09.2020
 */
@Slf4j
@Setter
@Component
@RequiredArgsConstructor
@ConfigurationProperties("app.scheduler")
public class SchedulerVerticle extends AbstractVerticle {

    // beans
    final CounterService service;
    // props
    long timer;


    @PostConstruct
    void info() {
        log.info("timer=[{}]", timer);
    }

    @Override
    public void start() throws Exception {
        super.start();
        log.debug("setting up timer");
        vertx.setPeriodic(timer, id -> service.updateCounter());
    }

}

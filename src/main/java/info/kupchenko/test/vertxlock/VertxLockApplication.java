package info.kupchenko.test.vertxlock;

import info.kupchenko.test.vertxlock.verticle.HttpVerticle;
import info.kupchenko.test.vertxlock.verticle.SchedulerVerticle;
import io.vertx.reactivex.core.RxHelper;
import io.vertx.reactivex.core.Vertx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class VertxLockApplication {

	// beans
	final Vertx vertx;
	final SchedulerVerticle schedulerVerticle;
	final HttpVerticle httpVerticle;

	@PostConstruct
	void deploy() {

		RxHelper.deployVerticle(vertx, schedulerVerticle)
				.subscribe(
						id -> log.info("SchedulerVerticle deployed with id [{}]", id),
						error -> log.error("SchedulerVerticle failed to deploy", error)
				);
		RxHelper.deployVerticle(vertx, httpVerticle)
				.subscribe(
						id -> log.info("HttpVerticle deployed with id [{}]", id),
						error -> log.error("HttpVerticle failed to deploy", error)
				);

	}

	public static void main(String[] args) {
		SpringApplication.run(VertxLockApplication.class, args);
	}

}

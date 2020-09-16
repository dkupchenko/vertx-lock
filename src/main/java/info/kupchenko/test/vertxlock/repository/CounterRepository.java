package info.kupchenko.test.vertxlock.repository;

import io.reactivex.Completable;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.shareddata.SharedData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Класс CounterRepository
 *
 * @author dkupchenko
 * created 15.09.2020
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CounterRepository {

    private static final long INIT_VAL = 1;
    // beans
    final Vertx vertx;
    final SharedData sharedData;
    // locals
    final ReentrantLock lock = new ReentrantLock();
    // resources protected by lock
    long counter;
    String hexCounter;


    @PostConstruct
    void info() {
        counter = INIT_VAL;
        hexCounter = Long.toHexString(counter);
        log.info("dec=[{}], hex=[{}]", counter, hexCounter);
    }

    public String getCounter() {
        if(!lock.tryLock()) return null;
        try {
            return Long.toString(counter);
        } finally {
            lock.unlock();
        }
    }

    public String getHexCounter() {
        if(!lock.tryLock()) return null;
        try {
            return hexCounter;
        } finally {
            lock.unlock();
        }
    }

    public Completable updateCounter() {
        return Completable.fromRunnable(() -> {
            lock.lock();
            try {
                // atomic block start
                log.debug("lock start");
                counter++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException error) {
                    log.error("sleep interrupted", error);
                }
                hexCounter = Long.toHexString(counter);
                // atomic block end
            } finally {
                lock.unlock();
            }
        });
    }

}

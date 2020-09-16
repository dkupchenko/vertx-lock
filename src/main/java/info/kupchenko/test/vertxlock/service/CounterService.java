package info.kupchenko.test.vertxlock.service;

import info.kupchenko.test.vertxlock.repository.CounterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * Класс CounterService
 *
 * @author dkupchenko
 * created 15.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CounterService {

    // beans
    final CounterRepository repository;


    @PostConstruct
    void info() {
        log.info("dec=[{}], hex=[{}]", getCounter(), getHexCounter());
    }

    public String getCounter() {
        return repository.getCounter();
    }

    public String getHexCounter() {
        return repository.getHexCounter();
    }

    public void updateCounter() {
        log.debug("UPDATE COUNTER FROM [{}]", repository.getCounter());
        repository
                .updateCounter()
                .subscribe(
                        () -> log.info("UPDATE COMPLETE [{}]", repository.getCounter()),
                        error -> log.error("Failed to update", error)
                );
    }

}

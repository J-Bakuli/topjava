package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

public interface IdGenerator {
    AtomicInteger lastId = new AtomicInteger(0);

    default Integer generateId() {
        return lastId.incrementAndGet();
    }
}

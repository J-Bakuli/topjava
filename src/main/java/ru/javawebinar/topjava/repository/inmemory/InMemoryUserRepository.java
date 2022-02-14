package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();

    {
        List<User> userList = new ArrayList<>();
        userList.add(new User(null, "Albert", "albert@mail.ru", "qwhi876!skljj", Role.ADMIN));
        userList.add(new User(null, "Judy", "judy@mail.ru", "ksd99229", Role.USER));
        userList.add(new User(null, "Abba", "abba@mail.ru", "898w7dd23'", Role.USER));
        userList.add(new User(null, "Kirill", "kirill@mail.ru", "HIHLADZ*^!", Role.USER));
        userList.add(new User(null, "Sveta", "sveta@mail.ru", ";OQEU**_897!%", Role.USER));
        userList.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName))
                .sorted(Comparator.comparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findAny().orElse(null);
    }
}

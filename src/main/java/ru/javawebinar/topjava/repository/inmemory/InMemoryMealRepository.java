package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        log.info("save{}", userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            System.out.println(meal.getId()); //for testing
            log.debug("saved {}, meal");
            return meal;
        }
        // handle case: update, but not present in storage
        meal.setUserId(userId);
        Meal updatedMeal = repository.computeIfPresent(meal.getId(),
                (id, oldMeal) -> oldMeal.getUserId().equals(userId) ? meal : oldMeal);
        System.out.println(updatedMeal); //for testing
        log.debug("saved {}", meal);
        return updatedMeal;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        log.info("delete {}", id);
        System.out.println(get(id, userId)); //for testing
        return repository.remove(id, get(id, userId));
    }

    @Override
    public Meal get(int id, Integer userId) {
        log.info("get {}", id);
        Meal meal = repository.getOrDefault(id, new Meal(null, null, 0));
        return userId.equals(meal.getUserId()) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        log.info("getAll {}");
        System.out.println(repository.values().parallelStream() //for testing
                .filter(meal -> userId.equals(meal.getUserId()))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList()));

        return repository.values().parallelStream()
                .filter(meal -> userId.equals(meal.getUserId()))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}


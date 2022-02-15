package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, MealDataBase> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    private void save(Meal meal) {
        save(meal, SecurityUtil.authUserId());
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save{}", userId);
        MealDataBase mdb = new MealDataBase(userId, meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), mdb);
            return meal;
        }
        log.debug("saved {}", meal);
        // handle case: update, but not present in storage
        return repository.getOrDefault(repository.computeIfPresent(meal.getId(), (id, oldMeal) -> mdb), mdb).meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return isKeyFromUserId(repository.get(id).key, userId) ? repository.get(id).meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll {}", userId);

        return repository.values().parallelStream()
                .filter(mealDataBase -> isKeyFromUserId(mealDataBase.key, userId))
                .map(mealDataBase -> mealDataBase.meal)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFilteredDuringDay(int userId) {
        log.info("getAllFilteredDuringDay {}", userId);
        return repository.values().parallelStream()
                .map(mealDataBase -> mealDataBase.meal)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    private boolean isKeyFromUserId(int key, int userId) {
        return key == userId;
    }

    private static class MealDataBase {
        private final int key;
        private final Meal meal;

        private MealDataBase(int userId, Meal meal) {
            this.key = userId;
            this.meal = meal;
        }
    }
}
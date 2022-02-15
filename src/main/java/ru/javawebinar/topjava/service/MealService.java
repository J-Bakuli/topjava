package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealRepository repository;

    public MealService(MealRepository mealRepository) {
        this.repository = mealRepository;
    }

    public Meal create(Meal meal, int userId) {
        log.info("create {} for userId {}", meal, userId);
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {} for userId {}", id, userId);
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} for userId {}", meal, userId);
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Meal get(int id, int userId) {
        log.info("get{} for userId {}", id, userId);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        log.info("getAll for userId {}", userId);
        return repository.getAll(userId);
    }

/*    public List<MealTo> getAll(int userId, int caloriesPerDayLimit) {
        log.info("getAll {} with calories limit {}", userId, caloriesPerDayLimit);
        return MealsUtil.getTos(repository.getAll(userId), caloriesPerDayLimit); //TODO to remove at the end
    }*/
}
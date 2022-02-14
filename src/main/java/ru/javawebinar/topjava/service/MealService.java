package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealRepository repository;

    public MealService(MealRepository mealRepository) {
        this.repository = mealRepository;
    }

    public Meal create(Meal meal, Integer userId) {
        log.info("create {}", meal, userId);
        return repository.save(meal, userId);
    }

    public void delete(int id, Integer userId) throws NotFoundException {
        log.info("delete {}", id, userId);
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Meal meal, Integer userId) throws NotFoundException {
        log.info("update{}", meal, userId);
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Meal get(int id, Integer userId) throws NotFoundException {
        log.info("get{}", id, userId);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(Integer userId) {
        log.info("getAll", userId);
        return repository.getAll(userId);
    }

    public List<MealTo> getAll(Integer userId, int caloriesPerDayLimit) {
        log.info("getAll", userId, caloriesPerDayLimit);
        System.out.println(MealsUtil.getTos(repository.getAll(userId), caloriesPerDayLimit)); //TODO to remove. used for testing in SpringMain
        return MealsUtil.getTos(repository.getAll(userId), caloriesPerDayLimit);
    }

}
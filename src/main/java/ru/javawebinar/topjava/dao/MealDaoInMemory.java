package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDaoInMemory implements MealDao {
    private static final Logger log = getLogger(MealServlet.class);
    private final Map<Long, Meal> meals = new ConcurrentHashMap<>();

    {
        List<Meal> mealsList = new ArrayList<>();
        mealsList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealsList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealsList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealsList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealsList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealsList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealsList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

        for (Meal meal : mealsList) {
            meals.put(meal.getId(), meal);
        }
    }

    @Override
    public void remove(long id) {
        meals.remove(id);
    }

    @Override
    public Meal get(long id) {
        return meals.get(id);
    }

    @Override
    public Meal update(Meal meal, Meal newMeal) {
        long id = meal.getId();
        if (meal.hashCode() == newMeal.hashCode()) {
            return null;
        }
        else {
            log.debug("MealDao, update contains key");
            Meal updatedMeal = new Meal(id, newMeal.getDateTime(), newMeal.getDescription(), newMeal.getCalories());
            meals.put(id, updatedMeal);
            return updatedMeal;
        }
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal add(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }
}


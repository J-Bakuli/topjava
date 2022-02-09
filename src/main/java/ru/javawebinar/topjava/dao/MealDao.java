package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao {
    Meal add(Meal meal);

    void remove(long id);

    Meal get(long id);

    Meal update(Meal meal, Meal newMeal);
}
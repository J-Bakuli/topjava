package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealDAO {
    void add(Meal meal);

    void remove(Meal meal);

    void remove(long id);

    Meal get(long id);
}
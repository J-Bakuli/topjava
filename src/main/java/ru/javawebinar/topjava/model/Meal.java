package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Meal implements IdGenerator {
    private long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.id = generateId();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(long id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealTo that = (MealTo) o;
        return /*id == that.getId() &&*/
                calories == that.getCalories() &&
                        Objects.equals(dateTime, that.getDateTime()) &&
                        Objects.equals(description, that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories); //w/o id, to change
    }

}

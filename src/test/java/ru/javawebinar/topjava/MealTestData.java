package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ;
    public static final int NOT_FOUND = 3;

    public static final Meal userMeal1 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(MEAL_ID + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(MEAL_ID + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(MEAL_ID + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal mealAdmin1 = new Meal(MEAL_ID + 10, LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0), "Завтрак Админа", 330);
    public static final Meal mealAdmin2 = new Meal(MEAL_ID + 11, LocalDateTime.of(2020, Month.JANUARY, 30, 12, 0), "Обед Админа", 600);
    public static final Meal mealAdmin3 = new Meal(MEAL_ID + 12, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Админа", 580);
    public static final Meal mealAdmin4 = new Meal(MEAL_ID + 13, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак Админа", 430);
    public static final Meal mealAdmin5 = new Meal(MEAL_ID + 14, LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Обед Админа", 700);
    public static final Meal mealAdmin6 = new Meal(MEAL_ID + 15, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Ужин Админа", 400);

    public static final List<Meal> userMeals = Arrays.asList(userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6, userMeal7);
    public static final List<Meal> adminMeals = Arrays.asList(mealAdmin1, mealAdmin2, mealAdmin3, mealAdmin4, mealAdmin5, mealAdmin6);

    public static Meal getCreated() {
        return new Meal(null, LocalDateTime.of(2044, Month.APRIL, 1, 9, 0), "Новый завтрак", 1200);
    }

    public static Meal getUpdated() {
        return new Meal(userMeal4.getId(), userMeal4.getDateTime(), "Updated завтрак", 890);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}

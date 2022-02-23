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

    public static final Meal Meal1 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal Meal2 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal Meal3 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal Meal4 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal Meal5 = new Meal(MEAL_ID + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal Meal6 = new Meal(MEAL_ID + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal Meal7 = new Meal(MEAL_ID + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal MealAdmin1 = new Meal(MEAL_ID + 10, LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0), "Завтрак Админа", 330);
    public static final Meal MealAdmin2 = new Meal(MEAL_ID + 11, LocalDateTime.of(2020, Month.JANUARY, 30, 12, 0), "Обед Админа", 600);
    public static final Meal MealAdmin3 = new Meal(MEAL_ID + 12, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Админа", 580);
    public static final Meal MealAdmin4 = new Meal(MEAL_ID + 13, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак Админа", 430);
    public static final Meal MealAdmin5 = new Meal(MEAL_ID + 14, LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Обед Админа", 700);
    public static final Meal MealAdmin6 = new Meal(MEAL_ID + 15, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Ужин Админа", 400);

    public static final List<Meal> userMeals = Arrays.asList(Meal1, Meal2, Meal3, Meal4, Meal5, Meal6, Meal7);
    public static final List<Meal> adminMeals = Arrays.asList(MealAdmin1, MealAdmin2, MealAdmin3, MealAdmin4, MealAdmin5, MealAdmin6);

    public static Meal getCreated() {
        return new Meal(null, LocalDateTime.of(2044, Month.APRIL, 1, 9, 0), "Новый завтрак", 1200);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID+6, Meal4.getDateTime(), "Updated завтрак", 890);
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

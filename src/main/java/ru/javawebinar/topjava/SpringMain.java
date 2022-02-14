package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            //mealRestController.create(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 14, 13, 40), "Завтрак", 400));
            //mealRestController.delete(1);
            //mealRestController.getAllTo();
            //mealRestController.get(2);
            //mealRestController.getAll();
            //mealRestController.getByFilteredToTime(1, LocalTime.of(12, 45), LocalTime.of(18, 00));
            //mealRestController.getByFilteredToDate(1, LocalDate.of(2020, Month.JANUARY, 28), LocalDate.of(2020, Month.JANUARY, 28));
            //mealRestController.update(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410), 5);
        }
    }
}

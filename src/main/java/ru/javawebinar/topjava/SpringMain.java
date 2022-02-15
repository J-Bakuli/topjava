package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class SpringMain {

    public static void main(String[] args) {

        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            // System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            // AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            // adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            InMemoryUserRepository userRepository = appCtx.getBean(InMemoryUserRepository.class);
            userRepository.getAll();
            userRepository.getByEmail("4");
            userRepository.get(2);

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.get(2);
            mealRestController.getAllTo();
            mealRestController.create(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 15, 14, 30), "Еда другого юзера", 4000));
            mealRestController.getByFilteredToDateTime(1,
                    LocalDate.of(2020, Month.JANUARY, 30),
                    LocalDate.of(2020, Month.JANUARY, 31),
                    LocalTime.of(10, 00),
                    LocalTime.of(20, 00));
        }
    }
}

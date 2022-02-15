package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }


    public List<MealTo> getAllTo() {
        log.info("getAllTo");
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getByFilteredToDateTime(int UserId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getFiltered by DateTime {} {} {} {}", startDate, endDate, startTime, endTime);
        System.out.println(MealsUtil.getFilteredToDateTime(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startDate, endDate, startTime, endTime)); //TODO to remove after testing
        return MealsUtil.getFilteredToDateTime(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startDate, endDate, startTime, endTime);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create {}", meal);
        return service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} for id {}", meal, meal.getId());
        service.update(meal, authUserId());
    }
}
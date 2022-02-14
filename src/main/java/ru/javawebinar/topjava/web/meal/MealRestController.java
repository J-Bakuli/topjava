package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAllTo() throws NotFoundException {
        log.info("getAllTo {}");
        return service.getAll(authUserId(), authUserCaloriesPerDay());
    }

    public Collection<MealTo> getAll() {
        log.info("getAllTo {}");
        return service.getAll(authUserId(), authUserCaloriesPerDay());
    }

    public List<MealTo> getByFilteredToDate(int UserId, LocalDate startDate, LocalDate endDate) {
        return MealsUtil.getFilteredToDate(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startDate, endDate);
    }

    public List<MealTo> getByFilteredToTime(int UserId, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredToTime(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create", meal);
        return service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update", meal, meal.getId());
        assureIdConsistent(meal, meal.getId());
        service.update(meal, authUserId());
    }
}


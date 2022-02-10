package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private final Logger log = getLogger(MealServlet.class);
    private final int CALORIES_PER_DAY = 2000;
    private final String NEW_MEALS_PATH = "addUpdateMeal.jsp";
    private final String ALL_MEALS_PATH = "meals.jsp";
    private MealDaoInMemory dao;
    private String forward = null;

    @Override
    public void init() throws ServletException {
        dao = new MealDaoInMemory();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        log.debug("MealServlet doGet, all meals list");
        request.setAttribute("meals", getMealsTo());
        forward = ALL_MEALS_PATH;

        if (action == null) {
            forward = ALL_MEALS_PATH;
            request.setAttribute("meals", getMealsTo());
            request.getRequestDispatcher(forward).forward(request, response);
        }

        switch (action.toLowerCase(Locale.ROOT)) {
            case "delete":
                log.debug("MealServlet doGet, delete");
                long deleteId = Long.parseLong(request.getParameter("id"));
                dao.remove(deleteId);
                forward = ALL_MEALS_PATH;
                request.setAttribute("meals", getMealsTo());
                break;
            case "edit":
                log.debug("MealServlet doGet, edit");
                long editId = Long.parseLong(request.getParameter("id"));
                Meal meal = dao.get(editId);
                forward = NEW_MEALS_PATH;
                request.setAttribute("meal", meal);
                break;
            case "insert":
                log.debug("MealServlet doGet, insert");
                forward = NEW_MEALS_PATH;
                break;
            default:
                log.debug("MealServlet doGet, default");
                forward = ALL_MEALS_PATH;
                break;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    private List<MealTo> getMealsTo() {
        List<Meal> meals = dao.getAll();
        List<MealTo> filteredWithExceeded = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        filteredWithExceeded.sort(Comparator.comparing(MealTo::getDateTime));
        return filteredWithExceeded;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet doPost");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealDateTime = request.getParameter("dateTime");
        LocalDateTime dateTime = LocalDateTime.parse(mealDateTime, DateTimeFormatter.ISO_DATE_TIME);

        if (id == null || id.isEmpty()) {
            log.debug("MealServlet doPost, add");
            Meal newMeal = new Meal(dateTime, description, calories);
            dao.add(newMeal);
        } else {
            log.debug("MealServlet doPost, edit");
            Meal meal = dao.get(Integer.parseInt(id));
            Meal newMeal = new Meal(dateTime, description, calories);
            dao.update(meal, newMeal);
        }
        request.setAttribute("meals", getMealsTo());
        request.getRequestDispatcher(ALL_MEALS_PATH).forward(request, response);
    }
}
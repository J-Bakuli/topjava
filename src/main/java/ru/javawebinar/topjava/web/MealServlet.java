package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.impl.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealsTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
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

    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final String EDITED_MEALS_PATH = "edited_meal.jsp";
    private static final String ALL_MEALS_PATH = "meals.jsp";
    private static MealDAOImpl dao;
    private static String forward = null;

/*    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDAOImpl();
    }*/

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
        log.debug("MealServlet doGet");
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
                log.debug("MealServlet doGet, delete meal");
                int deleteId = Integer.parseInt(request.getParameter("meal"));
                dao.remove(deleteId);
                forward = ALL_MEALS_PATH;
                request.setAttribute("meals", getMealsTo());
                break;
            case "edit":
                log.debug("MealServlet doGet, edit meal");
                forward = EDITED_MEALS_PATH;
                int editId = Integer.parseInt(request.getParameter("meal"));
                request.setAttribute("meals", dao.get(editId));
                break;
            case "listUser":
                log.debug("MealServlet doGet, get all meals");
                forward = ALL_MEALS_PATH;
                request.setAttribute("meals", getMealsTo());
                break;
            default:
                forward = EDITED_MEALS_PATH;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    private List<MealsTo> getMealsTo() {
        List<Meal> meals = dao.getAll();
        List<MealsTo> filteredWithExceeded = MealsUtil
                .filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        filteredWithExceeded.sort(Comparator.comparing(MealsTo::getDateTime));
        return filteredWithExceeded;
    }
}
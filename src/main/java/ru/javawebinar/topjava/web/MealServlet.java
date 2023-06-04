package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CollectionMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private static final String LIST_MEAL = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new CollectionMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward;
        String action = request.getParameter("action");
        switch (action == null ? "noAction" : action) {
            case "add":
                log.debug("add new meal");
                forward = INSERT_OR_EDIT;
                request.setAttribute("meal", MealsUtil.emptyMeal());
                request.setAttribute("status", "add");
                break;
            case "update":
                log.debug("update or edit meal");
                int id = Integer.parseInt(request.getParameter("id"));
                forward = INSERT_OR_EDIT;
                request.setAttribute("meal", mealDao.get(id));
                request.setAttribute("status", "edit");
                break;
            case "delete":
                log.debug("delete meal by id");
                id = Integer.parseInt(request.getParameter("id"));
                mealDao.delete(id);
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                response.sendRedirect("meals");
                return;
            default:
                log.debug("redirect to meals list");
                forward = LIST_MEAL;
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                break;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal;
        if (id == null || id.isEmpty()) {
            meal = new Meal(localDateTime, description, calories);
        } else {
            meal = new Meal(Integer.parseInt(id), localDateTime, description, calories);
        }
        mealDao.save(meal);
        response.sendRedirect("meals");
    }
}

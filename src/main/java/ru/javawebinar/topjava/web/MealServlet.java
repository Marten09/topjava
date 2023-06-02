package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CollectionMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private static final String LIST_MEAL = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private MealDao mealDao;
    private final int caloriesPerDay = 2000;

    @Override
    public void init() throws ServletException {
        mealDao = new CollectionMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward;
        String action = request.getParameter("action");
        if (action == null) {
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.of(0, 0), LocalTime.MAX, caloriesPerDay));
        } else {
            switch (action) {
                case "add" -> {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("meal", mealDao.add(new Meal(null, null, null, 0)));
                }
                case "update" -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("meal", mealDao.update(id));
                }
                case "delete" -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    mealDao.delete(id);
                    forward = LIST_MEAL;
                    request.setAttribute("meals", MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.of(0, 0), LocalTime.MAX, caloriesPerDay));
                }
                default -> forward = INSERT_OR_EDIT;
            }
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        mealDao.add(new Meal(id, localDateTime, description, calories));
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        request.setAttribute("meals", MealsUtil.filteredByStreams(mealDao.getAll(),
                LocalTime.of(0, 0), LocalTime.MAX, 2000));
        view.forward(request, response);
    }
}

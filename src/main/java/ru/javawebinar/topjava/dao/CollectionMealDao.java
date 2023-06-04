package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class CollectionMealDao implements MealDao {
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private final AtomicInteger countId = new AtomicInteger(0);
    private static final Logger log = getLogger(CollectionMealDao.class);

    public CollectionMealDao() {
        List<Meal> meals = MealsUtil.fillinglist();
        for (Meal meal : meals) {
            save(meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() != null) {
            return mealMap.computeIfPresent(meal.getId(), (id, mealNew) -> MealsUtil.getMealWithId(meal, id));
        }
        Meal newMeal = MealsUtil.getMealWithId(meal, countId.incrementAndGet());
        mealMap.put(newMeal.getId(), newMeal);
        return newMeal;
    }

    @Override
    public void delete(Integer id) {
        log.debug("delete by id");
        mealMap.remove(id);
    }

    @Override
    public Meal get(Integer id) {
        log.debug("get by id");
        return mealMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        log.debug("get all Meal");
        return new ArrayList<>(mealMap.values());
    }
}

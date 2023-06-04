package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal save(Meal meal);

    void delete(Integer id);

    Meal get(Integer id);

    List<Meal> getAll();
}

package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.MAX;
import static java.time.LocalDateTime.MIN;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, ConcurrentHashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(1, meal));
        MealsUtil.meals2.forEach(meal -> this.save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        ConcurrentHashMap<Integer, Meal> userMeals = repository.computeIfAbsent(userId, unused -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        ConcurrentHashMap<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        ConcurrentHashMap<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        ConcurrentHashMap<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? Collections.emptyList() : userMeals.values().stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), convertToStartLocalDateTime(startDate), convertToEndLocalDateTime(endDate)))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private LocalDateTime convertToStartLocalDateTime(LocalDate startDate) {
        return LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), MIN.getHour(), MIN.getMinute());
    }

    private LocalDateTime convertToEndLocalDateTime(LocalDate startDate) {
        return LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), MAX.getHour(), MAX.getMinute());
    }
}


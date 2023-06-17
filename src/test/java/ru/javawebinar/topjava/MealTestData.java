package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final Meal userMeal1 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(START_SEQ + 10, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Обед админа", 800);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 11, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин админа", 910);
    public static final Meal MEAL_NOT_FOUND = new Meal(START_SEQ + 15, LocalDateTime.of(2020, Month.MARCH, 31, 20, 0), "Ужин", 210);
    public static final int NOT_FOUND = 10;

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "newMeal", 1000);
    }

    public static Meal getUpdated() {
        return new Meal(userMeal1.getId(), LocalDateTime.of(2022, Month.MARCH, 30, 10, 0), "UPDATED", 1000);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}

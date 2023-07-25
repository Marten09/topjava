package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime parse(String text, Locale locale) {
        return LocalTime.parse(text, timeFormatter);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return localTime.toString();
    }
}

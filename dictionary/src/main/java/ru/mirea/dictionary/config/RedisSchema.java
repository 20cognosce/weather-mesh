package ru.mirea.dictionary.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisSchema {
    public static final String JANUARY = "January";
    public static final String FEBRUARY = "February";
    public static final String MARCH = "March";
    public static final String APRIL = "April";
    public static final String MAY = "May";
    public static final String JUNE = "June";
    public static final String JULY = "July";
    public static final String AUGUST = "August";
    public static final String SEPTEMBER = "September";
    public static final String OCTOBER = "October";
    public static final String NOVEMBER = "November";
    public static final String DECEMBER = "December";

    public static final String DESCRIPTION = "Description";
    public static final String DESCRIPTION_TEMPERATURE = "Среднемесячная температура воздуха, °С";
    public static final String DESCRIPTION_PRECIPITATION = "Месячная сумма осадков, мм";
    public static final String DESCRIPTION_SUNSHINE = "Продолжительность солнечного сияния, ч";

    public static final String MOSCOW = "Moscow";
    public static final String SAINT_PETERSBURG = "SaintPetersburg";
    public static final String KAZAN = "Kazan";
    public static final String NOVOROSSIYSK = "Novorossiysk";
    public static final String VLADIVOSTOK = "Vladivostok";
    public static final String OMSK = "Omsk";

    public static final String TEMPERATURE = "Temperature";
    public static final String PRECIPITATION = "Precipitation";
    public static final String SUNSHINE = "Sunshine";

    public static final List<String> MONTHS = Arrays.asList(JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER);
    public static final List<String> CITIES = Arrays.asList(MOSCOW, SAINT_PETERSBURG, KAZAN, NOVOROSSIYSK, VLADIVOSTOK, OMSK);
    public static final List<String> CONDITIONS = Arrays.asList(TEMPERATURE, PRECIPITATION, SUNSHINE);

    public static Map<String, String> mapMonthToValue(List<String> values, String description) {
        var map = MONTHS.stream()
                .collect(Collectors.toMap(
                        month -> month,
                        month -> values.get(MONTHS.indexOf(month))));

        map.put(DESCRIPTION, description);
        return map;
    }
}


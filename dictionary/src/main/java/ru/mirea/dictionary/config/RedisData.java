package ru.mirea.dictionary.config;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class RedisData {

    private final List<String> moscowTemperature = Arrays.asList("-6.2", "-5.9", "-0.7", "6.9", "13.6", "17.3", "19.7", "17.6", "11.9", "5.8", "-0.5", "-4.4");
    private final List<String> moscowPrecipitation = Arrays.asList("53", "44", "39", "37", "61", "77", "84", "78", "66", "70", "52", "51");
    private final List<String> moscowSunshine = Arrays.asList("31", "70", "127", "171", "263", "276", "273", "236", "144", "76", "33", "19");
    private final List<String> saintPetersburgTemperature = Arrays.asList("-4.8", "-5.0", "-1.0", "5.2", "11.5", "16.1", "19.1", "17.4", "12.4", "6.2", "0.9", "-2.5");
    private final List<String> saintPetersburgPrecipitation = Arrays.asList("46", "36", "35", "37", "47", "69", "83", "86", "57", "63", "56", "51");
    private final List<String> saintPetersburgSunshine = Arrays.asList("19", "45", "121", "178", "256", "254", "268", "228", "135", "62", "23", "8");
    private final List<String> kazanTemperature = Arrays.asList("-10", "-9.7", "-3.3", "5.8", "14.1", "18.3", "20.5", "18.3", "12.3", "5.3", "-2.5", "-7.9");
    private final List<String> kazanPrecipitation = Arrays.asList("46", "37", "38", "33", "38", "57", "63", "54", "50", "54", "45", "50");
    private final List<String> kazanSunshine = Arrays.asList("36", "72", "144", "213", "290", "309", "316", "253", "156", "78", "33", "21");
    private final List<String> omskTemperature = Arrays.asList("-16.9", "-14.6", "-6.6", "4.7", "13", "18", "19.4", "17", "10.6", "3.8", "-6.9", "-13.9");
    private final List<String> omskPrecipitation = Arrays.asList("21", "18", "19", "26", "31", "55", "65", "56", "29", "33", "35", "29");
    private final List<String> omskSunshine = Arrays.asList("84", "130", "201", "237", "307", "317", "297", "257", "178", "121", "72", "55");
    private final List<String> vladivostokTemperature = Arrays.asList("-11.9", "-8.1", "-1.5", "5.3", "10", "13.8", "18.1", "20", "16.3", "9.2", "-0.7", "-9.2");
    private final List<String> vladivostokPrecipitation = Arrays.asList("12", "16", "27", "43", "97", "104", "155", "176", "103", "67", "36", "19");
    private final List<String> vladivostokSunshine = Arrays.asList("178", "181", "210", "182", "170", "131", "120", "150", "198", "195", "160", "150");
    private final List<String> novorossiyskTemperature = Arrays.asList("4.1", "4.3", "7.3", "11.9", "17.2", "22.1", "25.2", "25.7", "20.7", "15.1", "9.6", "5.9");
    private final List<String> novorossiyskPrecipitation = Arrays.asList("90", "73", "76", "52", "47", "56", "65", "62", "66", "66", "70", "98");
    private final List<String> novorossiyskSunshine = Arrays.asList("82", "101", "139", "181", "230", "265", "304", "286", "229", "169", "113", "79");
}

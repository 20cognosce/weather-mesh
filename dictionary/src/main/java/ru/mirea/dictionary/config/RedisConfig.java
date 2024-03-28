package ru.mirea.dictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Map;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public StringRedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setEnableTransactionSupport(false);
        template.afterPropertiesSet();

        template.opsForHash().putAll("Moscow.Temperature", moscowTemperature());
        template.opsForHash().putAll("Moscow.Precipitation", moscowPrecipitation());
        template.opsForHash().putAll("Moscow.Sunshine", moscowSunshine());

        template.opsForHash().putAll("SaintPetersburg.Temperature", saintPetersburgTemperature());
        template.opsForHash().putAll("SaintPetersburg.Precipitation", saintPetersburgPrecipitation());
        template.opsForHash().putAll("SaintPetersburg.Sunshine", saintPetersburgSunshine());

        template.opsForHash().putAll("Kazan.Temperature", kazanTemperature());
        template.opsForHash().putAll("Kazan.Precipitation", kazanPrecipitation());
        template.opsForHash().putAll("Kazan.Sunshine", kazanSunshine());

        template.opsForHash().putAll("Omsk.Temperature", omskTemperature());
        template.opsForHash().putAll("Omsk.Precipitation", omskPrecipitation());
        template.opsForHash().putAll("Omsk.Sunshine", omskSunshine());

        template.opsForHash().putAll("Vladivostok.Temperature", vladivostokTemperature());
        template.opsForHash().putAll("Vladivostok.Precipitation", vladivostokPrecipitation());
        template.opsForHash().putAll("Vladivostok.Sunshine", vladivostokSunshine());

        template.opsForHash().putAll("Novorossiysk.Temperature", novorossiyskTemperature());
        template.opsForHash().putAll("Novorossiysk.Precipitation", novorossiyskPrecipitation());
        template.opsForHash().putAll("Novorossiysk.Sunshine", novorossiyskSunshine());

        return template;
    }

    Map<String, String> moscowTemperature() {
        return Map.ofEntries(
                Map.entry("January", "-6.2"),
                Map.entry("February ", "-5.9"),
                Map.entry("March", "-0.7"),
                Map.entry("April", "6.9"),
                Map.entry("May", "13.6"),
                Map.entry("June", "17.3"),
                Map.entry("July", "19.7"),
                Map.entry("August", "17.6"),
                Map.entry("September", "11.9"),
                Map.entry("October", "5.8"),
                Map.entry("November", "-0.5"),
                Map.entry("December", "-4.4"),
                Map.entry("Description", "Среднемесячная температура воздуха, °С"));
    }

    Map<String, String> moscowPrecipitation() {
        return Map.ofEntries(
                Map.entry("January", "53"),
                Map.entry("February ", "44"),
                Map.entry("March", "39"),
                Map.entry("April", "37"),
                Map.entry("May", "61"),
                Map.entry("June", "77"),
                Map.entry("July", "84"),
                Map.entry("August", "78"),
                Map.entry("September", "66"),
                Map.entry("October", "70"),
                Map.entry("November", "52"),
                Map.entry("December", "51"),
                Map.entry("Description", "Месячная сумма осадков, мм"));
    }

    Map<String, String> moscowSunshine() {
        return Map.ofEntries(
                Map.entry("January", "31"),
                Map.entry("February", "70"),
                Map.entry("March", "127"),
                Map.entry("April", "171"),
                Map.entry("May", "263"),
                Map.entry("June", "276"),
                Map.entry("July", "273"),
                Map.entry("August", "236"),
                Map.entry("September", "144"),
                Map.entry("October", "76"),
                Map.entry("November", "33"),
                Map.entry("December", "19"),
                Map.entry("Description", "Продолжительность солнечного сияния, ч"));
    }

    /************************************************/

    Map<String, String> saintPetersburgTemperature() {
        return Map.ofEntries(
                Map.entry("January", "-4.8"),
                Map.entry("February", "-5.0"),
                Map.entry("March", "-1.0"),
                Map.entry("April", "5.2"),
                Map.entry("May", "11.5"),
                Map.entry("June", "16.1"),
                Map.entry("July", "19.1"),
                Map.entry("August", "17.4"),
                Map.entry("September", "12.4"),
                Map.entry("October", "6.2"),
                Map.entry("November", "0.9"),
                Map.entry("December", "-2.5"),
                Map.entry("Description", "Среднемесячная температура воздуха, °С"));
    }

    Map<String, String> saintPetersburgPrecipitation() {
        return Map.ofEntries(
                Map.entry("January", "46"),
                Map.entry("February", "36"),
                Map.entry("March", "35"),
                Map.entry("April", "37"),
                Map.entry("May", "47"),
                Map.entry("June", "69"),
                Map.entry("July", "83"),
                Map.entry("August", "86"),
                Map.entry("September", "57"),
                Map.entry("October", "63"),
                Map.entry("November", "56"),
                Map.entry("December", "51"),
                Map.entry("Description", "Месячная сумма осадков, мм"));
    }

    Map<String, String> saintPetersburgSunshine() {
        return Map.ofEntries(
                Map.entry("January", "19"),
                Map.entry("February", "45"),
                Map.entry("March", "121"),
                Map.entry("April", "178"),
                Map.entry("May", "256"),
                Map.entry("June", "254"),
                Map.entry("July", "268"),
                Map.entry("August", "228"),
                Map.entry("September", "135"),
                Map.entry("October", "62"),
                Map.entry("November", "23"),
                Map.entry("December", "8"),
                Map.entry("Description", "Продолжительность солнечного сияния, ч"));
    }

    /************************************************/

    Map<String, String> kazanTemperature() {
        return Map.ofEntries(
                    Map.entry("January", "-10"),
                    Map.entry("February", "-9.7"),
                    Map.entry("March", "-3.3"),
                    Map.entry("April", "5.8"),
                    Map.entry("May", "14.1"),
                    Map.entry("June", "18.3"),
                    Map.entry("July", "20.5"),
                    Map.entry("August", "18.3"),
                    Map.entry("September", "12.3"),
                    Map.entry("October", "5.3"),
                    Map.entry("November", "-2.5"),
                    Map.entry("December", "-7.9"),
                    Map.entry("Description", "Среднемесячная температура воздуха, °С"));
    }

    Map<String, String> kazanPrecipitation() {
        return Map.ofEntries(
                Map.entry("January", "46"),
                Map.entry("February", "37"),
                Map.entry("March", "38"),
                Map.entry("April", "33"),
                Map.entry("May", "38"),
                Map.entry("June", "57"),
                Map.entry("July", "63"),
                Map.entry("August", "54"),
                Map.entry("September", "50"),
                Map.entry("October", "54"),
                Map.entry("November", "45"),
                Map.entry("December", "50"),
                Map.entry("Description", "Месячная сумма осадков, мм"));
    }

    Map<String, String> kazanSunshine() {
        return Map.ofEntries(
                Map.entry("January", "36"),
                Map.entry("February", "72"),
                Map.entry("March", "144"),
                Map.entry("April", "213"),
                Map.entry("May", "290"),
                Map.entry("June", "309"),
                Map.entry("July", "316"),
                Map.entry("August", "253"),
                Map.entry("September", "156"),
                Map.entry("October", "78"),
                Map.entry("November", "33"),
                Map.entry("December", "21"),
                Map.entry("Description", "Продолжительность солнечного сияния, ч"));
    }

    /************************************************/

    Map<String, String> omskTemperature() {
        return Map.ofEntries(
                Map.entry("January", "-16.9"),
                Map.entry("February", "-14.6"),
                Map.entry("March", "-6.6"),
                Map.entry("April", "4.7"),
                Map.entry("May", "13"),
                Map.entry("June", "18"),
                Map.entry("July", "19.4"),
                Map.entry("August", "17"),
                Map.entry("September", "10.6"),
                Map.entry("October", "3.8"),
                Map.entry("November", "-6.9"),
                Map.entry("December", "-13.9"),
                Map.entry("Description", "Среднемесячная температура воздуха, °С"));
    }

    Map<String, String> omskPrecipitation() {
        return Map.ofEntries(
                Map.entry("January", "21"),
                Map.entry("February", "18"),
                Map.entry("March", "19"),
                Map.entry("April", "26"),
                Map.entry("May", "31"),
                Map.entry("June", "55"),
                Map.entry("July", "65"),
                Map.entry("August", "56"),
                Map.entry("September", "29"),
                Map.entry("October", "33"),
                Map.entry("November", "35"),
                Map.entry("December", "29"),
                Map.entry("Description", "Месячная сумма осадков, мм"));
    }

    Map<String, String> omskSunshine() {
        return Map.ofEntries(
                Map.entry("January", "84"),
                Map.entry("February", "130"),
                Map.entry("March", "201"),
                Map.entry("April", "237"),
                Map.entry("May", "307"),
                Map.entry("June", "317"),
                Map.entry("July", "297"),
                Map.entry("August", "257"),
                Map.entry("September", "178"),
                Map.entry("October", "121"),
                Map.entry("November", "72"),
                Map.entry("December", "55"),
                Map.entry("Description", "Продолжительность солнечного сияния, ч"));
    }

    /************************************************/

    Map<String, String> vladivostokTemperature() {
        return Map.ofEntries(
                Map.entry("January", "-11.9"),
                Map.entry("February", "-8.1"),
                Map.entry("March", "-1.5"),
                Map.entry("April", "5.3"),
                Map.entry("May", "10"),
                Map.entry("June", "13.8"),
                Map.entry("July", "18.1"),
                Map.entry("August", "20"),
                Map.entry("September", "16.3"),
                Map.entry("October", "9.2"),
                Map.entry("November", "-0.7"),
                Map.entry("December", "-9.2"),
                Map.entry("Description", "Среднемесячная температура воздуха, °С"));
    }

    Map<String, String> vladivostokPrecipitation() {
        return Map.ofEntries(
                Map.entry("January", "12"),
                Map.entry("February", "16"),
                Map.entry("March", "27"),
                Map.entry("April", "43"),
                Map.entry("May", "97"),
                Map.entry("June", "104"),
                Map.entry("July", "155"),
                Map.entry("August", "176"),
                Map.entry("September", "103"),
                Map.entry("October", "67"),
                Map.entry("November", "36"),
                Map.entry("December", "19"),
                Map.entry("Description", "Месячная сумма осадков, мм"));
    }

    Map<String, String> vladivostokSunshine() {
        return Map.ofEntries(
                Map.entry("January", "178"),
                Map.entry("February", "181"),
                Map.entry("March", "210"),
                Map.entry("April", "182"),
                Map.entry("May", "170"),
                Map.entry("June", "131"),
                Map.entry("July", "120"),
                Map.entry("August", "150"),
                Map.entry("September", "198"),
                Map.entry("October", "195"),
                Map.entry("November", "160"),
                Map.entry("December", "150"),
                Map.entry("Description", "Продолжительность солнечного сияния, ч"));
    }


    /************************************************/

    Map<String, String> novorossiyskTemperature() {
            return Map.ofEntries(
                    Map.entry("January", "4.1"),
                    Map.entry("February", "4.3"),
                    Map.entry("March", "7.3"),
                    Map.entry("April", "11.9"),
                    Map.entry("May", "17.2"),
                    Map.entry("June", "22.1"),
                    Map.entry("July", "25.2"),
                    Map.entry("August", "25.7"),
                    Map.entry("September", "20.7"),
                    Map.entry("October", "15.1"),
                    Map.entry("November", "9.6"),
                    Map.entry("December", "5.9"),
                    Map.entry("Description", "Среднемесячная температура воздуха, °С"));
    }

    Map<String, String> novorossiyskPrecipitation() {
            return Map.ofEntries(
                    Map.entry("January", "90"),
                    Map.entry("February", "73"),
                    Map.entry("March", "76"),
                    Map.entry("April", "52"),
                    Map.entry("May", "47"),
                    Map.entry("June", "56"),
                    Map.entry("July", "65"),
                    Map.entry("August", "62"),
                    Map.entry("September", "66"),
                    Map.entry("October", "66"),
                    Map.entry("November", "70"),
                    Map.entry("December", "98"),
                    Map.entry("Description", "Месячная сумма осадков, мм"));
    }

    Map<String, String> novorossiyskSunshine() {
         return Map.ofEntries(
                Map.entry("January", "82"),
                Map.entry("February", "101"),
                Map.entry("March", "139"),
                Map.entry("April", "181"),
                Map.entry("May", "230"),
                Map.entry("June", "265"),
                Map.entry("July", "304"),
                Map.entry("August", "286"),
                Map.entry("September", "229"),
                Map.entry("October", "169"),
                Map.entry("November", "113"),
                Map.entry("December", "79"),
                Map.entry("Description", "Продолжительность солнечного сияния, ч"));
    }
}

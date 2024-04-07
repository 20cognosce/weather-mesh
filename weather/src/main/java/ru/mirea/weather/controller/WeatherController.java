package ru.mirea.weather.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.mirea.auth.AuthRole;
import ru.mirea.auth.Role;
import ru.mirea.dto.DictionaryOptionsDto;
import ru.mirea.dto.DictionaryRequestDto;
import ru.mirea.dto.DictionaryResponseDto;
import ru.mirea.service.UtilService;

@RequestMapping("/weather")
@RestController
public class WeatherController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${hostname.dictionary}")
    private String dictionaryServiceHost;

    @Value("${hostname.auth}")
    private String authServiceHost;

    @AuthRole(Role.USER)
    @PostMapping("/info")
    public ResponseEntity<DictionaryResponseDto> getWeatherInfo(@RequestBody DictionaryRequestDto requestDto) {
        String token = UtilService.getTokenFromAuthServiceLogin(authServiceHost, applicationName, applicationName, UtilService.readPassword());

        var dictionaryServiceResponse = RestClient.create().post()
                .uri(dictionaryServiceHost + "/dictionary/info")
                .header(HttpHeaders.AUTHORIZATION, token)
                .header("request-from", applicationName)
                .header("request-to", "dictionary")
                .body(requestDto)
                .retrieve()
                .body(DictionaryResponseDto.class);

        return ResponseEntity.ok(dictionaryServiceResponse);
    }

    @AuthRole(Role.USER)
    @GetMapping("/options")
    public ResponseEntity<DictionaryOptionsDto> getWeatherRequestOptions() {
        String token = UtilService.getTokenFromAuthServiceLogin(authServiceHost, applicationName, applicationName, UtilService.readPassword());

        var dictionaryServiceResponse = RestClient.create().get()
                .uri(dictionaryServiceHost + "/dictionary/options")
                .header(HttpHeaders.AUTHORIZATION, token)
                .header("request-from", applicationName)
                .header("request-to", "dictionary")
                .retrieve()
                .body(DictionaryOptionsDto.class);

        return ResponseEntity.ok(dictionaryServiceResponse);
    }
}

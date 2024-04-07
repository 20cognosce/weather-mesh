package ru.mirea.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.auth.AuthRole;
import ru.mirea.auth.Role;
import ru.mirea.dictionary.config.RedisSchema;
import ru.mirea.dictionary.repo.DictionaryRedisRepository;
import ru.mirea.dto.DictionaryOptionsDto;
import ru.mirea.dto.DictionaryRequestDto;
import ru.mirea.dto.DictionaryResponseDto;
import ru.mirea.service.UtilService;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/dictionary")
@RestController
public class DictionaryController {

    @Value("${spring.application.name}")
    private String applicationName;

    private final DictionaryRedisRepository repo;

    @AuthRole(Role.SYSTEM)
    @PostMapping("/info")
    public ResponseEntity<DictionaryResponseDto> getDictionaryInfo(@RequestBody DictionaryRequestDto requestDto) {
        UtilService.logRequest(applicationName);

        if (!isRequestValid(requestDto)) {
            return ResponseEntity
                    .badRequest()
                    .body(new DictionaryResponseDto(null, "The request failed validation"));
        }

        List<String> result = repo.getData(requestDto.getCity(), requestDto.getCondition(), requestDto.getMonth());
        return ResponseEntity.ok(DictionaryResponseDto.builder()
                .value(result.get(0))
                .description(result.get(1))
                .build());
    }

    @AuthRole(Role.SYSTEM)
    @GetMapping("/options")
    public ResponseEntity<DictionaryOptionsDto> getDictionaryRequestOptions() {
        UtilService.logRequest(applicationName);

        return ResponseEntity.ok(
                DictionaryOptionsDto.builder()
                        .options(Map.of(
                                "city", RedisSchema.CITIES,
                                "condition", RedisSchema.CONDITIONS,
                                "month", RedisSchema.MONTHS))
                        .description("Перечень доступных значений для параметров запроса /info сервиса справочных данных")
                        .build()
        );
    }

    private boolean isRequestValid(DictionaryRequestDto req) {
        return RedisSchema.CITIES.contains(req.getCity()) &&
                RedisSchema.CONDITIONS.contains(req.getCondition()) &&
                RedisSchema.MONTHS.contains(req.getMonth());
    }
}

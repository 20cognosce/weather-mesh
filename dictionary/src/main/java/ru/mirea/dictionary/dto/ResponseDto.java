package ru.mirea.dictionary.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String value;
    private String description;
}

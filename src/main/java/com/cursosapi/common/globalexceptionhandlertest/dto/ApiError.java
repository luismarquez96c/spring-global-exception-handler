package com.cursosapi.common.globalexceptionhandlertest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    private String message;

    @JsonProperty(value = "http-code")
    private int httpCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonProperty(value = "sub-messages")
    @Builder.Default
    private List<String> subMessages = new ArrayList<>();

}

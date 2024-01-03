package com.harishs461.URLShortner.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorMessage {

    private String message;
    private String description;
    private int statusCode;

}

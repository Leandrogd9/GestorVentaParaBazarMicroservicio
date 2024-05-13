package com.bazar.ventasservice.exception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestException extends RuntimeException{
    private List<String> messagesList;
}

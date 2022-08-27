package dev.gniadek.githubrestdemo.dto.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralExceptionDTO {
    private Integer status;
    private String Message;
}

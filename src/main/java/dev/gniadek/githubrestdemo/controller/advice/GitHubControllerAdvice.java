package dev.gniadek.githubrestdemo.controller.advice;

import dev.gniadek.githubrestdemo.dto.exceptions.GeneralExceptionDTO;
import dev.gniadek.githubrestdemo.utils.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GitHubControllerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GeneralExceptionDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralExceptionDTO(404, e.getMessage()));
    }

}

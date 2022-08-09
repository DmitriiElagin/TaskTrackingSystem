package elagin.dmitry.tasktrackingservice.controller;

import elagin.dmitry.tasktrackingservice.entities.ErrorEntity;
import elagin.dmitry.tasktrackingservice.exception.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<ErrorEntity> handleEntityNotFoundExceptions(Exception exception) {
        return new ResponseEntity<>(new ErrorEntity(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            {ConstraintViolationException.class,
                    MethodArgumentNotValidException.class,
                    IllegalArgumentException.class})
    public ResponseEntity<ErrorEntity> handleValidationExceptions(Exception exception) {
        return new ResponseEntity<>(new ErrorEntity(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorEntity> handleExceptions(Exception exception) {
        return new ResponseEntity<>(new ErrorEntity(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


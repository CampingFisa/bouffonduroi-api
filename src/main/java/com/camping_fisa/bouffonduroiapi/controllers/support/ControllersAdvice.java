package com.camping_fisa.bouffonduroiapi.controllers.support;

import com.camping_fisa.bouffonduroiapi.controllers.questions.dto.ErrorMessage;
import com.camping_fisa.bouffonduroiapi.exceptions.BusinessException;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllersAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}

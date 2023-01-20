package com.graccasoft.drones.errorhandling;

import com.graccasoft.drones.exception.DroneNotFoundException;
import com.graccasoft.drones.exception.DroneCanNotBeLoadedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 5:02 pm
 *
 * Handle exceptions so that client can have a Json response to work wit
 */
@ControllerAdvice
public class AppEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest webRequest){

        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                new ArrayList<>());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DroneCanNotBeLoadedException.class})
    public final ResponseEntity<ErrorDetails> handleDroneReachedWeightLimitException(Exception ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                new ArrayList<>());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DroneNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleDroneNotFoundException(Exception ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                new ArrayList<>());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * When user provide invalid input like drone serial number more than 100 characters, we want them to get the exact error
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ArrayList<String> details = new ArrayList<>();
        ex.getFieldErrors().forEach( error->{
           details.add(error.getDefaultMessage()) ;
        });
        ErrorDetails errorDetails  = new ErrorDetails(LocalDate.now(),
                "Invalid input provided",
                request.getDescription(false),
                details);



        return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}

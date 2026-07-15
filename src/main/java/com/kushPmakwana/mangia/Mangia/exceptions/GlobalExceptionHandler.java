package com.kushPmakwana.mangia.Mangia.exceptions;

import com.kushPmakwana.mangia.Mangia.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRNFE(ResourcesNotFoundException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.NOT_FOUND.value());
        err.setEntityName(e.getEntityName());
        err.setStatus(HttpStatus.NOT_FOUND);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public  ResponseEntity<ErrorDTO> handleAEE(AlreadyExistsException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.CONFLICT.value());
        err.setEntityName(e.getEntityName());
        err.setStatus(HttpStatus.CONFLICT);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(CustomException.class)
    public  ResponseEntity<ErrorDTO> handleCustomException(CustomException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.setEntityName(e.getEntityName());
        err.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public  ResponseEntity<ErrorDTO> handleIRE(InvalidRoleException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        err.setEntityName("LOGIN");
        err.setStatus(HttpStatus.UNAUTHORIZED);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(UnmodifiableException.class)
    public  ResponseEntity<ErrorDTO> handleUnmodifiableException(UnmodifiableException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.FORBIDDEN.value());
        err.setEntityName(e.getEntityName());
        err.setStatus(HttpStatus.FORBIDDEN);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(DisabledAccountException.class)
    public  ResponseEntity<ErrorDTO> handleDisabledException(DisabledAccountException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.FORBIDDEN.value());
        err.setEntityName("USER-LOGIN");
        err.setStatus(HttpStatus.FORBIDDEN);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorDTO> handleUnmodifiableException(UnAuthorizedException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        err.setEntityName("USER-LOGIN");
        err.setStatus(HttpStatus.UNAUTHORIZED);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(TableNotAvailableException.class)
    public ResponseEntity<ErrorDTO> handleTableNotFoundException(TableNotAvailableException e){
        ErrorDTO err = new ErrorDTO();
        err.setStatusCode(HttpStatus.NOT_FOUND.value());
        err.setEntityName("RESTAURANT-TABLE");
        err.setStatus(HttpStatus.NOT_FOUND);
        err.setMessage(e.getMessage());
        err.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}

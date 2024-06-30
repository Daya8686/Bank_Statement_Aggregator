package com.BankStatementAggregator.errorhandiling;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	@Autowired
	private ErrorResponse errorResponse;
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex, BindingResult result) {

		// Create ErrorResponse object and populate with validation errors
		
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage("Validation error");
		errorResponse.setTimestamp(LocalDateTime.now());

		// Extract field errors and populate error details
		List<FieldError> fieldErrors = result.getFieldErrors();
		Map<String, String> errors = new HashMap<>();
		for (FieldError fieldError : fieldErrors) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		errorResponse.setErrors(errors);

		return errorResponse;
	}

//	@ExceptionHandler( TypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleTypeMismatchException( TypeMismatchException ex,
			BindingResult bindingResult) {
			errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			errorResponse.setTimestamp(LocalDateTime.now());
			errorResponse.setMessage("Type Mismatch");
		return errorResponse;

	}
	
//	@ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
       
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("JSON parse error: " + ex.getLocalizedMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = ife.getPath().stream()
                                   .map(ref -> ref.getFieldName())
                                   .findFirst()
                                   .orElse("Unknown field");

            String errorMessage = String.format("Invalid value for field '%s': %s", fieldName, ife.getValue());
            errorResponse.setMessage(errorMessage);
        } else {
            errorResponse.setMessage(ex.getLocalizedMessage());
        }

        return errorResponse;
    }
	
//	@ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Duplicate entry or data integrity violation");
        errorResponse.setTimestamp(LocalDateTime.now());
        // You can add more specific handling based on the exception message or type
        
        
        return errorResponse;
    }
	
	
	@ExceptionHandler( Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleAllExceptions(Exception e) {
		errorResponse.setMessage("Unexceped Error "+ e.getLocalizedMessage());
		errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return errorResponse;
		
	}
	
//	@ExceptionHandler( EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
	
		errorResponse.setMessage("Entity not found");
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		return errorResponse;
		
	}
	
//	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
		
		errorResponse.setMessage("Validation Error");
		errorResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return errorResponse;
	}
	
//	@ExceptionHandler(SQLException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleSQLException(SQLException ex, WebRequest request) {
		errorResponse.setMessage("Exception in Data Base");
		errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorResponse.setTimestamp(LocalDateTime.now());
		return errorResponse;
	}
	
	@ExceptionHandler(UserServiceException.class)
	@ResponseBody
	public ErrorResponse handlingUserServiceExceptions(UserServiceException ex) {
		errorResponse.setStatus(ex.getHttpStatus().value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return errorResponse;
	}
	
	@ExceptionHandler(CompanyServiceExceptionHandler.class)
	@ResponseBody
	public ErrorResponse handlingCompanyServiceException (CompanyServiceExceptionHandler ex) {
		errorResponse.setStatus(ex.getHttpStatus().value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return errorResponse;
	}
	
	@ExceptionHandler(BranchServiceExceptionHandler.class)
	@ResponseBody
	public ErrorResponse handlingBranchServiceException (BranchServiceExceptionHandler ex) {
		errorResponse.setStatus(ex.getHttpStatus().value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return errorResponse;
	}
	
	
	@ExceptionHandler(BankServiceExceptionHandler.class)
	@ResponseBody
	public ErrorResponse handlingBankServiceException (BankServiceExceptionHandler ex) {
		errorResponse.setStatus(ex.getHttpStatus().value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return errorResponse;
	}
	
	@ExceptionHandler(BankStatementGenerationExceptionHandler.class)
	@ResponseBody
	public ErrorResponse handlingBankStatementGenerationExceptionHandler (BankStatementGenerationExceptionHandler ex) {
		errorResponse.setStatus(ex.getHttpStatus().value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return errorResponse;
	}
	
	
	
	
	
}

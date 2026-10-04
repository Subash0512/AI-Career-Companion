package com.example.AI.Resume.Maker.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.AI.Resume.Maker.dto.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================================================
    // INVALID RESUME
    // =========================================================

    @ExceptionHandler(InvalidResumeException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidResume(
            InvalidResumeException exception,
            WebRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid Resume",
                exception.getMessage(),
                request
        );
    }

    // =========================================================
    // VALIDATION ERRORS
    // =========================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException exception,
            WebRequest request) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": "
                                + error.getDefaultMessage()
                )
                .collect(Collectors.joining(", "));

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                message,
                request
        );
    }

    // =========================================================
    // INVALID ARGUMENT
    // =========================================================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException exception,
            WebRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                exception.getMessage(),
                request
        );
    }

    // =========================================================
    // MISSING REQUEST PARAMETER
    // =========================================================

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException exception,
            WebRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Missing Parameter",
                exception.getParameterName() + " is required.",
                request
        );
    }

    // =========================================================
    // FILE TOO LARGE
    // =========================================================

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUploadSize(
            MaxUploadSizeExceededException exception,
            WebRequest request) {

        return buildResponse(
                HttpStatusCode.valueOf(413),
                "File Too Large",
                "The uploaded file exceeds the maximum allowed size.",
                request
        );
    }

    // =========================================================
    // AI SERVICE ERROR
    // =========================================================

    @ExceptionHandler(AiServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleAiServiceException(
            AiServiceException exception,
            WebRequest request) {

        return buildResponse(
                HttpStatus.BAD_GATEWAY,
                "AI Service Error",
                exception.getMessage(),
                request
        );
    }

    // =========================================================
    // GENERAL ERROR
    // =========================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(
            Exception exception,
            WebRequest request) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred.",
                request
        );
    }

    // =========================================================
    // COMMON RESPONSE BUILDER
    // =========================================================

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatusCode status,
            String error,
            String message,
            WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        error,
                        message,
                        getPath(request)
                );

        return ResponseEntity
                .status(status)
                .body(response);
    }

    // =========================================================
    // REQUEST PATH
    // =========================================================

    private String getPath(WebRequest request) {

        String description =
                request.getDescription(false);

        if (description.startsWith("uri=")) {
            return description.substring(4);
        }

        return description;
    }
}
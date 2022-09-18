package io.test.code.dynamicmenu.common.exception;

import io.test.code.dynamicmenu.common.dto.response.CommonNotValidResponse;
import io.test.code.dynamicmenu.common.dto.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.naming.NotContextException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice("io.test.code.dynamicmenu")
@Slf4j
public class CommonExceptionAdvice {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> exception(Exception e) {
        e.printStackTrace();
        return CommonResponse.<String>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(e.getMessage())
                .build();
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> runtimeException(Exception e) {
        e.printStackTrace();
        return CommonResponse.<String>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(e.getMessage())
                .build();
    }
    @ExceptionHandler({CommonException.class})
    public ResponseEntity<CommonResponse<?>> commonException(CommonException e) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(e.getErrorCode())
                .message(e.getErrorMessage())
                .data(e.getDetail())
                .build(), e.getHttpStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<List<CommonNotValidResponse>> constraintViolationException(ConstraintViolationException e){
        return CommonResponse.<List<CommonNotValidResponse>>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(e.getConstraintViolations()
                        .stream()
                        .map(constraintViolation -> CommonNotValidResponse.builder()
                                .field(constraintViolation.getPropertyPath().toString())
                                .rejectedValue(constraintViolation.getInvalidValue())
                                .message(constraintViolation.getMessage())
                                .build())
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<List<CommonNotValidResponse>> methodArgumentNotValidException(MethodArgumentNotValidException e){
        return CommonResponse.<List<CommonNotValidResponse>>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(e.getFieldErrors()
                        .stream()
                        .map(fieldError -> CommonNotValidResponse.builder()
                                .field(fieldError.getField())
                                .rejectedValue(fieldError.getRejectedValue())
                                .message(fieldError.getDefaultMessage())
                                .build())
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResponse<String> authenticateException(Exception e) {
        return CommonResponse.<String>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .data(e.getMessage())
                .build();
    }
}

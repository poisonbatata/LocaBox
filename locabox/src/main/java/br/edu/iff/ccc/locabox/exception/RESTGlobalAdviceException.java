package br.edu.iff.ccc.locabox.exception;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RESTGlobalAdviceException {

    @ExceptionHandler(UserNotExist.class)
    public ProblemDetail handleUserNotExist(UserNotExist ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Recurso não encontrado");
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Erro de validação nos campos");
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(req.getRequestURI()));
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Violação de constraint");
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("violations", ex.getConstraintViolations().toString());
        return pd;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        String msg = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        HttpStatus status = msg != null && msg.toLowerCase().contains("email") ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, msg);
        pd.setTitle(status == HttpStatus.CONFLICT ? "Conflito de dados" : "Bad Request");
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "JSON inválido ou incompleto");
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(ToolNotExist.class)
    public ProblemDetail handleToolNotExist(ToolNotExist ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Recurso não encontrado");
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    // Converte erros de path params inválidos (ex.: /tool/abc) em 400 com ProblemDetail
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Parâmetro inválido: " + ex.getName());
        pd.setTitle("Bad Request");
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("expectedType",
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        pd.setProperty("value", ex.getValue());
        return pd;
    }
}
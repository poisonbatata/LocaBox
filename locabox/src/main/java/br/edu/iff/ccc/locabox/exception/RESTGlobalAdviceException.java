package br.edu.iff.ccc.locabox.exception;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.edu.iff.ccc.locabox.exception.RentalNotAvaible;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RESTGlobalAdviceException {

    private ProblemDetail buildProblem(HttpStatus status, String message, HttpServletRequest req, Exception ex, String Title) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, message);
        pd.setTitle(Title);
        pd.setProperty("url", req.getRequestURL().toString());
        pd.setProperty("Timestamp", Instant.now().toString());
        pd.setProperty("status", HttpStatusCode.valueOf(pd.getStatus()).toString());
        pd.setProperty("message", message);
        pd.setProperty("exception", ex.getClass().getName());
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(UserNotExist.class)
    public ProblemDetail handleUserNotExist(UserNotExist ex, HttpServletRequest req) {
        String titulo = "Usuário não encontrado";
        return buildProblem(HttpStatus.NOT_FOUND, ex.getMessage(), req, ex, titulo);
    }

    @ExceptionHandler(ToolNotExist.class)
    public ProblemDetail handleToolNotExist(ToolNotExist ex, HttpServletRequest req) {
        String titulo = "Produto não encontrado";
        return buildProblem(HttpStatus.NOT_FOUND, ex.getMessage(), req, ex, titulo);
    }

    @ExceptionHandler(RentalNotExist.class)
    public ProblemDetail handleRentalNotExist(RentalNotExist ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.NOT_FOUND, ex.getMessage(), req, ex, "Recurso não encontrado");
    }

    @ExceptionHandler(RentalNotAvaible.class)
    public ProblemDetail handleRentalNotAvailable(RentalNotAvaible ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.CONFLICT, ex.getMessage(), req, ex, "Ferramenta indisponível para o período solicitado");
    }

    @ExceptionHandler(RentalStartInPast.class)
    public ProblemDetail handleRentalStartInPast(RentalStartInPast ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.CONFLICT, ex.getMessage(), req, ex, "Data de início inválida");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String titulo = "Erro de validação nos campos";
        ProblemDetail pd = buildProblem(HttpStatus.BAD_REQUEST, "Erro de validação nos campos", req, ex, titulo);
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        String titulo = "Violação de constraint";
        return buildProblem(HttpStatus.BAD_REQUEST, "Violação de constraint", req, ex, titulo);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        String msg = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        HttpStatus status = msg != null && msg.toLowerCase().contains("email") ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
        String titulo = "Violação de integridade de dados";
        return buildProblem(status, msg, req, ex, titulo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        String titulo = "JSON inválido ou incompleto";
        return buildProblem(HttpStatus.BAD_REQUEST, "JSON inválido ou incompleto", req, ex, titulo);
    }

    // Converte erros de path params inválidos (ex.: /tool/abc) em 400 com ProblemDetail
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        String msg = "Parâmetro inválido: " + ex.getName();
        String titulo = "Parâmetro inválido";
        ProblemDetail pd = buildProblem(HttpStatus.BAD_REQUEST, msg, req, ex, titulo);
        pd.setProperty("expectedType", ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        pd.setProperty("value", ex.getValue());
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.BAD_REQUEST, ex.getMessage(), req, ex, "Bad Request");
    }
}
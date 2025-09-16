package br.edu.iff.ccc.locabox.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = Controller.class)
public class ViewGlobalAdviceException {
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("erro", e);
        mav.addObject("path", req.getRequestURL());
        mav.addObject("message", e.getMessage());
        mav.addObject("status", 500);
        mav.setViewName("error");
        return mav;
    }

}

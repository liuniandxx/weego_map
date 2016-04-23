package me.weego.controller;

import me.weego.pojo.ResBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tcl
 */
@RestController
public class BaseController {
    @ExceptionHandler(Exception.class)
    public ResBody exceptionHandler(Exception ex) {
        return ResBody.returnFail(-1, ex.getMessage());
    }
}

package de.nicolasschlecker.vvsmarthomeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class JsonErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public JsonErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public @ResponseBody
    Map<String, Object> handleError(HttpServletRequest req) {
        final var webRequest = new ServletWebRequest(req);
        return errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return null;
    }
}

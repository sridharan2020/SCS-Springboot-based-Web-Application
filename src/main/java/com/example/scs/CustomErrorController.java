package com.example.scs;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import com.example.scs.model.User;
import com.example.scs.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class CustomErrorController  implements ErrorController {

    @Autowired
    SecurityService securityService;

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        String errorPage = "error"; // default

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        boolean log = Boolean.FALSE;
        User user = securityService.findLoggedInUser();
        if(user!=null) {log=Boolean.TRUE;}
        model.addAttribute("log", log);
        boolean admin = Boolean.FALSE;
        boolean member = Boolean.FALSE;
        boolean student = Boolean.FALSE;
        boolean oadmin = Boolean.FALSE;
        if(user!=null) {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
            admin = Boolean.TRUE;
        }
            if(Objects.equals(user.getRole(), "SCS_MEMBER")) {
                member = Boolean.TRUE;
            }
            if(Objects.equals(user.getRole(), "STUDENT")) {
                student = Boolean.TRUE;
            }
            if(Objects.equals(user.getRole(), "ADMIN")) {
                oadmin = Boolean.TRUE;
            }
        }
        model.addAttribute("admin", admin);
        model.addAttribute("member", member);
        model.addAttribute("student", student);
        model.addAttribute("oadmin", oadmin);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "error/404";

            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // handle HTTP 403 Forbidden error
                errorPage = "error/403";

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle HTTP 500 Internal Server error
                errorPage = "error/500";

            }
        }

        return errorPage;
    }

//    @Override
    public String getErrorPath() {
        return "/error";
    }
}
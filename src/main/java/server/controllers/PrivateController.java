package server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/private")
@SessionAttributes("username")
public class PrivateController {
    @RequestMapping("/*")
    public ModelAndView handlePrivateRequests(HttpServletRequest request) {
        String servlet = request.getServletPath();
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        boolean isLoggedIn = (boolean) session.getAttribute("loggedin");
        System.out.println("/private " + session.getAttribute("loggedin"));
        if (isLoggedIn) {
            mv.setViewName("uploadForm");
        } else {
            mv.setViewName("accessdenied");
        }

        return mv;
    }
}
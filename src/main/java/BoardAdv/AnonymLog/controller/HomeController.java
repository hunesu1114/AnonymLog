package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
@Slf4j
public class HomeController {

    @GetMapping("")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Member testerLogin = (Member) session.getAttribute(SessionConst.TESTER_LOGIN);
        if (testerLogin == null) {
            model.addAttribute("loginStatus", false);
            log.info("loginStatus : {}",model.getAttribute("loginStatus"));
            return "home";
        }
        if (testerLogin.getIsTester() == true) {
            model.addAttribute("loginStatus", true);
            log.info("loginStatus : {}",model.getAttribute("loginStatus"));
        }
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
}

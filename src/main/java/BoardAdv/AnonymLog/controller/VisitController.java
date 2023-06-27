package BoardAdv.AnonymLog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitController {

    @GetMapping("")
    public String toHome() {
        return "redirect:/home";
    }
}

package BoardAdv.AnonymLog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitController {

    /**
     * 도메인만 치고 들어왔을 때 '/home' 으로 redirect
     */
    @GetMapping("")
    public String toHome() {
        return "redirect:/home";
    }

    /**
     * 서버에서 '/board/list' 로 redirect 할 때, 1번 페이지로 가도록 함
     */
    @GetMapping("/board/list")
    public String toList() {
        return "redirect:/board/list/1";
    }
}

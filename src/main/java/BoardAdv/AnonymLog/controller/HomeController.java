package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.service.MemberService;
import BoardAdv.AnonymLog.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberService memberService;
    @Value("${kakao.rest.api.key}")
    private String CLIENT_ID;

    /**
     * 로그인 시, session에 담긴 Member의 isTester가 true인 경우(tester또는 HEN) Model에 loginStatus true 담아서 전달
     */
    @GetMapping("")
    public String home(HttpServletRequest request, Model model) {
        memberService.addLoginStatusAttribute(request, model);
        return "home";
    }

    /**
     * 로그인 화면으로
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("restapikey", CLIENT_ID);
        return "login/login";
    }
}

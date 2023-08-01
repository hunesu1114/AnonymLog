package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.SessionLoginDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class SessionLoginController {

    private final MemberService memberService;

    /**
     * 로그인
     */
    @GetMapping("/login/session")
    public String sessionLogin(Model model, @RequestParam(required = false) Optional<String> trial) {
        if (trial.orElse("none").equals("fail")) {
            model.addAttribute("trialFailure", true);
        }
        SessionLoginDto sessionLoginDto = new SessionLoginDto();
        model.addAttribute("sessionLoginDto", sessionLoginDto);
        return "login/sessionlogin";
    }

    /**
     * 로그인 완료시 redirect
     */
    @PostMapping("/login/session")
    public String sessionLogin(@ModelAttribute SessionLoginDto sessionLoginDto, HttpServletRequest request) {
        Member tester = memberService.login(sessionLoginDto, request);
        log.info("tester : {}", tester.getNickname());
        if (tester == null) {
            log.info("tester == null");
            return "redirect:/home/login/session?trial=fail";
        }

        return "redirect:/home";
    }

    /**
     * 로그아웃 -> session.invalidate
     */
    @GetMapping("/logout")
    public String sessionLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/home";
    }
}

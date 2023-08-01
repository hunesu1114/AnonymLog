package BoardAdv.AnonymLog.service;

import BoardAdv.AnonymLog.dto.SessionLoginDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.repository.MemberRepository;
import BoardAdv.AnonymLog.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member login(SessionLoginDto sessionLoginDto, HttpServletRequest request) {
        Member tester = memberRepository.findByNickname(sessionLoginDto.getNickname());
        if (tester == null) {
            log.info("login() : 해당 ID 없음");
            return null;
        }
        if (tester.getPassword().equals(sessionLoginDto.getPassword())) {

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.TESTER_LOGIN, tester);
            return (Member) session.getAttribute(SessionConst.TESTER_LOGIN);
        } else {
            return null;
        }
    }

    /**
     * getTesterFromSession() 로 받은 testerFromSession이
     * null 이 아니면 loginStatus에 true를 담아 model 전송
     * null 이면 loginStatus에 false를 담아 model 전송
     */
    public void addLoginStatusAttribute(HttpServletRequest request, Model model) {
        Member testerFromSession = getTesterFromSession(request);
        if (testerFromSession == null) {
            model.addAttribute("loginStatus", false);
            log.info("loginStatus : {}",model.getAttribute("loginStatus"));
        } else {
            model.addAttribute("loginStatus", true);
            log.info("loginStatus : {}", model.getAttribute("loginStatus"));
        }
    }

    /**
     * sessionMember == null -> return null
     * sessionMember == tester -> return sessionMember
     * sessionMember !=null && sessionMember !=tester -> return null
     */
    public Member getTesterFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member sessionMember = (Member) session.getAttribute(SessionConst.TESTER_LOGIN);
        if (sessionMember == null) {
            log.info("MemberService.getTesterFromSession : [sessionMember == null]");
            return null;
        } else if (sessionMember.getIsTester() == true) {
            log.info("MemberService.getTesterFromSession : [sessionMember == {}]",sessionMember.getNickname());
            return sessionMember;
        } else{
            log.info("MemberService.getTesterFromSession : [sessionMember == {} (NO TESTER)]", sessionMember.getNickname());
            return null;
        }
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }


}

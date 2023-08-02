package BoardAdv.AnonymLog.service;

import BoardAdv.AnonymLog.dto.SessionLoginDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Role;
import BoardAdv.AnonymLog.repository.MemberRepository;
import BoardAdv.AnonymLog.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
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
            session.setAttribute(SessionConst.USER_LOGIN, tester);
            return (Member) session.getAttribute(SessionConst.TESTER_LOGIN);
        } else {
            return null;
        }
    }

    public Member kakaoLogin(Member member, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.USER_LOGIN, member);
        return (Member) session.getAttribute(SessionConst.USER_LOGIN);
    }

    /**
     * sessionMember가
     * 일반 회원이면 loginStatus : true, testerLoginStatus : false
     * 테스터이면 loginStatus : true, testerLoginStatus : true
     */
    public void addLoginStatusAttribute(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Member sessionMember = (Member) session.getAttribute(SessionConst.USER_LOGIN);
        if (sessionMember == null) {
            model.addAttribute("loginStatus", false);
            model.addAttribute("testerLoginStatus", false);
            model.addAttribute("adminLoginStatus", false);
        } else if(sessionMember.getRole()==Role.ROLE_USER){
            model.addAttribute("loginStatus", true);
            model.addAttribute("testerLoginStatus", false);
            model.addAttribute("adminLoginStatus", false);
        } else if(sessionMember.getRole()==Role.ROLE_TESTER){
            model.addAttribute("loginStatus", true);
            model.addAttribute("testerLoginStatus", true);
            model.addAttribute("adminLoginStatus", false);
        } else{
            model.addAttribute("loginStatus", true);
            model.addAttribute("testerLoginStatus", true);
            model.addAttribute("adminLoginStatus", true);
        }
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member createMemberFromKakao(Long idFromKakaoResponse, String nickname) {
        Member member = memberRepository.findByKakaoId(idFromKakaoResponse);
        if (member != null) {
            log.info("이미 kakao로 회원가입 된 계정");
            return member;
        }

        // TODO : email 처리 어떻게 할 지 고민 => 카카오에서 사업자 등록 안하면 이메일 동의 선택으로만 할 수 있도록 해놔서...
        return Member.builder()
                .kakaoId(idFromKakaoResponse)
                .nickname(nickname)
                .role(Role.ROLE_USER)
                .build();
    }

    public Member findMemberByKakaoId(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }


}

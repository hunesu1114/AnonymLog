package BoardAdv.AnonymLog.service;

import BoardAdv.AnonymLog.dto.SessionLoginDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.repository.MemberRepository;
import BoardAdv.AnonymLog.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member login(SessionLoginDto sessionLoginDto, HttpServletRequest request) {
        Member tester = memberRepository.findByNickname(sessionLoginDto.getNickname());
        if (tester == null) {
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

    public Member save(Member member) {
        return memberRepository.save(member);
    }


}

package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.KakaoTokenResponse;
import BoardAdv.AnonymLog.dto.KakaoUserInfoResponse;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Role;
import BoardAdv.AnonymLog.service.MemberService;
import BoardAdv.AnonymLog.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoController {
    private final OAuthService oAuthService;
    private final MemberService memberService;

//    "회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다.
//    인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다.사용자 정보를 이용하여 서비스에 회원가입합니다."
    @GetMapping("/oauth")
    public String kakaoOauth(@RequestParam("code") String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = oAuthService.getToken(code);
        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = oAuthService.getUserInfo(kakaoTokenResponse.getAccess_token());
        log.info("회원 정보 입니다.{}",userInfo);

        memberService.save(oAuthService.createMember(userInfo.getKakao_account().getEmail()));
        return "redirect:/";
    }
}

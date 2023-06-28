package BoardAdv.AnonymLog.config;

import BoardAdv.AnonymLog.dto.MemberDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.service.MemberService;
import BoardAdv.AnonymLog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class initData {

    private final MemberService memberService;
    private final PostService postService;

    @Value("${hen.password}")
    private String henPassword;

    @Value("${tester.password}")
    private String testerPassword;


    @EventListener(ApplicationReadyEvent.class)
    public void initPostData() {

        PostDto testPost1 = new PostDto();
        testPost1.setWriter("종민");
        testPost1.setPassword("1");
        testPost1.setTitle("밥먹자");
        testPost1.setContent("나 정말 오목천 그 도넛카페 너무야");
        testPost1.setIsBlind(false);

        postService.savePost(testPost1);


        PostDto testPost2 = new PostDto();
        testPost2.setWriter("철용");
        testPost2.setPassword("2");
        testPost2.setTitle("롤하자");
        testPost2.setContent("나 칼바람 지금 너무야. 여친잘때 빨리 해야댐");
        testPost2.setIsBlind(true);

        postService.savePost(testPost2);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initTestMemberData() {
        Member tester=Member.builder()
                .isTester(true)
                .nickname("TESTER")
                .password(testerPassword)
                .isHen(false)
                .build();

        Member hen=Member.builder()
                .isTester(true)
                .nickname("HEN")
                .password(henPassword)
                .isHen(true)
                .build();

        memberService.save(tester);
        memberService.save(hen);
    }
}

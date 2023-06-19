package BoardAdv.AnonymLog.config;

import BoardAdv.AnonymLog.dto.MemberDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.mapper.MemberMapper;
import BoardAdv.AnonymLog.service.MemberService;
import BoardAdv.AnonymLog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class initData {

    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final PostService postService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
        MemberDto memberDto1 = new MemberDto();
        memberDto1.setNickname("종민");
        memberDto1.setPassword("1");
        memberDto1.setIsHen(false);

        MemberDto memberDto2 = new MemberDto();
        memberDto2.setNickname("철용");
        memberDto2.setPassword("2");
        memberDto2.setIsHen(false);

        Member member1 = memberService.save(memberDto1);
        Member member2 = memberService.save(memberDto2);

        PostDto testPost1 = new PostDto();
        testPost1.setTitle("밥먹자");
        testPost1.setContent("나 정말 오목천 그 도넛카페 너무야");
        testPost1.setIsBlind(false);

        PostDto testPost2 = new PostDto();
        testPost2.setTitle("롤하자");
        testPost2.setContent("나 칼바람 지금 너무야. 여친잘때 빨리 해야댐");
        testPost2.setIsBlind(false);

        postService.savePost(testPost1,member1);
        postService.savePost(testPost2,member2);
    }
}

package BoardAdv.AnonymLog.config;

import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
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

    private final PostService service;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
        PostDto testPost1 = new PostDto();
        testPost1.setWrittenBy("종민");
        testPost1.setPassword("1");
        testPost1.setTitle("밥먹자");
        testPost1.setContent("나 정말 오목천 그 도넛카페 너무야");
        testPost1.setIsBlind(false);

        PostDto testPost2 = new PostDto();
        testPost2.setWrittenBy("철용");
        testPost2.setPassword("2");
        testPost2.setTitle("롤하자");
        testPost2.setContent("나 칼바람 지금 너무야. 여친잘때 빨리 해야댐");
        testPost2.setIsBlind(false);

        service.savePost(testPost1);
        service.savePost(testPost2);
    }
}

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
        PostDto testPost1 = PostDto.builder()
                .writtenBy("종민")
                .password("1")
                .title("밥먹자")
                .content("나 정말 오목천 그 도넛카페 너무야")
                .isBlind(false)
                .build();
        PostDto testPost2 = PostDto.builder()
                .writtenBy("철용")
                .password("2")
                .title("롤하자")
                .content("나 칼바람 지금 너무야. 여친잘때 빨리 해야댐")
                .isBlind(false)
                .build();
        service.savePost(testPost1);
        service.savePost(testPost2);
    }
}

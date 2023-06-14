package BoardAdv.AnonymLog.mapper;

import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * PostDto를 Post엔티티로 매핑해주는 클래스
 */
@Component
public class PostMapper {

    public Post postDtoToEntity(PostDto dto) {
        Post entity = Post.builder()
                .member(dto.getMember())
                .title(dto.getTitle())
                .content(dto.getContent())
                .time(getTimeString(LocalDateTime.now()))
                .build();
        return entity;
    }

    public PostDto postEntityToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setMember(post.getMember());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setIsBlind(post.getIsBlind());
        return dto;
    }

    public String getTimeString(LocalDateTime postTime) {
        int month = postTime.getMonth().getValue();
        int day = postTime.getDayOfMonth();
        int hour = postTime.getHour();
        int minute = postTime.getMinute();
        StringBuilder string = new StringBuilder();
        string.append(month);
        string.append("/");
        string.append(day);
        string.append(" ");
        string.append(hour);
        string.append(":");
        if (minute < 10) {
            string.append("0");
        }
        string.append(minute);
        return string.toString();
    }
}

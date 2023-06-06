package BoardAdv.AnonymLog.config;

import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoToEntityMapper {

    public Post postDtoToEntity(PostDto dto) {
        Post entity=Post.builder()
                .writtenBy(dto.getWrittenBy())
                .password(dto.getPassword())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        return entity;
    }
}

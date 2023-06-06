package BoardAdv.AnonymLog.service;

import BoardAdv.AnonymLog.config.DtoToEntityMapper;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final DtoToEntityMapper mapper;

    public Post savePost(PostDto dto) {
        Post post = mapper.postDtoToEntity(dto);
        return postRepository.save(post);
    }


}

package BoardAdv.AnonymLog.service;

import BoardAdv.AnonymLog.mapper.PostMapper;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper mapper;

    public Post savePost(PostDto dto) {
        Post post = mapper.postDtoToEntity(dto);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }
    public Post updatePost(Long postId,PostDto dto) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.update(dto);
        post.setTimeString(mapper.getTimeString(LocalDateTime.now()));
        return post;
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public PostDto postEntityToDto(Post post) {
        return mapper.postEntityToDto(post);
    }

}

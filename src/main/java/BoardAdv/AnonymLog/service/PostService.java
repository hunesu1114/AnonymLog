package BoardAdv.AnonymLog.service;

import BoardAdv.AnonymLog.dto.CommentDto;
import BoardAdv.AnonymLog.entity.Comment;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.mapper.PostMapper;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.repository.CommentRepository;
import BoardAdv.AnonymLog.repository.MemberRepository;
import BoardAdv.AnonymLog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    private final PostMapper mapper;

    public Post savePost(PostDto dto) {
        Post post = mapper.postDtoToEntity(dto);
        return postRepository.save(post);
    }

    public Comment saveComment(CommentDto dto) {
        Comment comment = Comment.builder()
                .member(dto.getMember())
                .post(dto.getPost())
                .content(dto.getContent())
                .build();
        return commentRepository.save(comment);
    }

    public void updateComment(Long commentId, CommentDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.updateComment(dto);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findAllByPage(Pageable pageable) {
        return postRepository.findAll(pageable).getContent();
    }

    public int getPostCnt() {
        return postRepository.findAll().size();
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

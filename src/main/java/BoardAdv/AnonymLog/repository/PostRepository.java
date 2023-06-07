package BoardAdv.AnonymLog.repository;

import BoardAdv.AnonymLog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Post save(Post post);

}

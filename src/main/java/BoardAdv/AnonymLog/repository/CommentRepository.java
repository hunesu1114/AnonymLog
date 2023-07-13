package BoardAdv.AnonymLog.repository;

import BoardAdv.AnonymLog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}

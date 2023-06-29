package BoardAdv.AnonymLog.repository;

import BoardAdv.AnonymLog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

}

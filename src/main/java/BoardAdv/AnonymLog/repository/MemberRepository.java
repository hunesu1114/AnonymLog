package BoardAdv.AnonymLog.repository;

import BoardAdv.AnonymLog.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByNickname(String nickname);

    @Query("select m from Member m where m.kakaoId=:id")
    Member findByKakaoId(@Param("id") Long id);
}

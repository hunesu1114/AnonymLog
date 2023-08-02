package BoardAdv.AnonymLog.entity;

import BoardAdv.AnonymLog.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO : kakaoId 칼럼 순서 두번째로
    private Long kakaoId;
    private String nickname;
    private String password;
    private String email;

    @Column(name="ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
}

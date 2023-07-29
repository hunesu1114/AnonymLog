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

    private String nickname;
    private String password;
    private String email;
    private Boolean isHen;
    private Boolean isTester;

    @Column(name="ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
}

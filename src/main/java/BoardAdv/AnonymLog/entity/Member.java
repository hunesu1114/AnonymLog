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
    private Boolean isHen;
    private Boolean isTester;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
}

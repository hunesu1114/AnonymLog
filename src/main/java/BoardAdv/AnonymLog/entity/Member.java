package BoardAdv.AnonymLog.entity;

import BoardAdv.AnonymLog.dto.MemberDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String password;
    private Boolean isHen;

    public Member setHen(Boolean isHen) {
        this.isHen = isHen;
        return this;
    }

    public Member update(MemberDto dto) {
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        return this;
    }
}

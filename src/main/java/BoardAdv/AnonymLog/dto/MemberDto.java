package BoardAdv.AnonymLog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    public MemberDto() {
    }

    private String nickname;
    private String password;
    private Boolean isHen;
}

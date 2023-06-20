package BoardAdv.AnonymLog.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MemberDto {

    public MemberDto() {
    }

    private String nickname;
    private String password;
    private Boolean isHen;
}

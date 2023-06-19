package BoardAdv.AnonymLog.dto;

import BoardAdv.AnonymLog.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    public PostDto() {
    }

    private String password;
    private String title;
    private String content;
    private Boolean isBlind;

}

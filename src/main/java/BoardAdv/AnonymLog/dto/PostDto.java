package BoardAdv.AnonymLog.dto;

import BoardAdv.AnonymLog.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostDto {

    public PostDto() {
    }

    private String writer;
    private String password;
    private String title;
    private String content;
    private Boolean isBlind;

}

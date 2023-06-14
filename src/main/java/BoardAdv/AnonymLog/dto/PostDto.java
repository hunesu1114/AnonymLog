package BoardAdv.AnonymLog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private String writtenBy;
    private String password;
    private String title;
    private String content;
    private Boolean isBlind;

}

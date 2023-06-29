package BoardAdv.AnonymLog.dto;

import BoardAdv.AnonymLog.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class PostDto {

    public PostDto() {
    }

    @NotBlank(message = "작성자 필수 기재")
    private String writer;

    @NotBlank(message = "비밀번호 필수기재")
    private String password;

    @NotBlank(message = "제목 필수")
    private String title;

    @NotBlank(message = "내용 필수")
    private String content;
    private Boolean isBlind;

}

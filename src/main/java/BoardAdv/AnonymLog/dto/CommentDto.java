package BoardAdv.AnonymLog.dto;

import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Post;
import lombok.Data;

@Data
public class CommentDto {

    public CommentDto() {
    }

    private Member member;
    private Post post;
    private String content;
}

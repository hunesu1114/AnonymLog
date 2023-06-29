package BoardAdv.AnonymLog.entity;

import BoardAdv.AnonymLog.dto.PostDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;
    private String password;
    private String title;
    private String content;
    private Boolean isBlind;
    private String time;

    /**
     * setter 사용하지 않고 update 전용 메서드 작성
     *
     * PostService.class의 updatePost 메서드에서 사용
     * @param dto
     * @return
     */
    public Post update(PostDto dto) {
        this.writer = dto.getWriter();
        this.password = dto.getPassword();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.isBlind = dto.getIsBlind();
        return this;
    }

    /**
     * 엔티티 수정할 때는 바로 위의 update 메서드 사용하는데, 엔티티 클래스에 mapper DI하기보다 서비스 단에서 수정일시를
     * 세팅해주기 위해 아래 메서드 사용
     *
     * 해당 메서드는 PostService.class 의 updatePost 메서드에서 사용
     * @param timeString
     * @return
     */
    public Post setTimeString(String timeString) {
        this.time = timeString;
        return this;
    }


}

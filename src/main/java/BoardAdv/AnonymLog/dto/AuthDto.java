package BoardAdv.AnonymLog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthDto {
    private Long id;
    private String title;
    private String password;
}

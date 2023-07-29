package BoardAdv.AnonymLog.dto;

import lombok.Data;

@Data
public class KakaoAccount {
    private Boolean hasEmail;
    private Boolean emailNeedsAgreement;
    private Boolean isEmailValid;
    private Boolean isEmailVerified;
    private String email;
}

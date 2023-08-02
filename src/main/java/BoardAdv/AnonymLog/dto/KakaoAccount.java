package BoardAdv.AnonymLog.dto;

import lombok.Data;

@Data
public class KakaoAccount {
    private Boolean hasEmail;
    private Boolean emailNeedsAgreement;
    private Boolean isEmailValid;
    private Boolean isEmailVerified;
    private String email;

    private Boolean profile_nickname_needs_agreement;
    private Profile profile;
}

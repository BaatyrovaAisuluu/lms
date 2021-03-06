package kg.peaksoft.peaksoftlmsbb4.db.dto.authentification;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class AuthRequestDto {
    private String email;
    private String password;
}

package propensi.myjisc.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;


@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequestDTO {

    String firstname;

    String lastname;

    String username;

    private String password;


}

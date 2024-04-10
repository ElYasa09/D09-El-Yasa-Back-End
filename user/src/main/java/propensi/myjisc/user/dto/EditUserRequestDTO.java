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

    @NotBlank(message = "Nama required")
    String firstname;

    @NotBlank(message = "Nama required")
    String lastname;


}

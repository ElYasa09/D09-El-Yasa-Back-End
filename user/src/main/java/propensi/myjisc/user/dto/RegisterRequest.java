package propensi.myjisc.user.dto;

import propensi.myjisc.user.model.Role;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private UUID id;
  private String firstname;
  private String lastname;
  private String username;
  private String email;
  private String password;
  private Role role;
}
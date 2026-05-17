package kz.alibek.sharemate.users;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserCreateDto {
    String name;
    @Email
    String email;
}

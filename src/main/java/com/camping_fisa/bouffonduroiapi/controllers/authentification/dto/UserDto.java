package com.camping_fisa.bouffonduroiapi.controllers.authentification.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String username;
    private @Email String email;
    private String address;
    private String friendAddress;
    private boolean isAdmin;

}
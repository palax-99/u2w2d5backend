package antoninopalazzolo.u2w2d5backend.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotBlank(message = "Il campo non può rimanere vuoto!")
        @Size(min = 2, max = 10)
        String username,
        @NotBlank(message = "Il campo non può rimanere vuoto!")
        @Size(min = 2, max = 10)
        String name,
        @NotBlank(message = "Il campo non può rimanere vuoto!")
        @Size(min = 2, max = 10)
        String surname,
        @NotBlank(message = "Il campo non può rimanere vuoto!")
        @Email(message = "Il formato dell'email non è corretto!")
        String email) {
}

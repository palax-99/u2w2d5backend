package antoninopalazzolo.u2w2d5backend.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsWithListDTO(
        List<String> error,
        String message,
        LocalDateTime timestamp) {
}
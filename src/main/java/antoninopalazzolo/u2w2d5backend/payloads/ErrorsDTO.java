package antoninopalazzolo.u2w2d5backend.payloads;

import java.time.LocalDateTime;

// Payload di risposta per gli errori generici
// Viene restituito dall'ExceptionsHandler(quando l'andrò a creare) quando scatta un'eccezione
// message = descrizione dell'errore
// timestamp = momento esatto in cui è avvenuto l'errore
public record ErrorsDTO(
        String message,
        LocalDateTime timestamp

) {
}

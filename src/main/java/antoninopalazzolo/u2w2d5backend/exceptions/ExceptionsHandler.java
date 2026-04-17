package antoninopalazzolo.u2w2d5backend.exceptions;

import antoninopalazzolo.u2w2d5backend.payloads.ErrorsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

// Questa annotazione dice a Spring che questa classe è il gestore globale delle eccezioni
// Ogni volta che nel codice viene lanciata un'eccezione, arriva qui
// Ha N metodi, ognuno dedicato a gestire un tipo specifico di eccezione
@RestControllerAdvice
public class ExceptionsHandler {

    // @ExceptionHandler specifica quale tipo di eccezione gestisce questo metodo
    // @ResponseStatus imposta il codice HTTP della risposta
    // Quando viene lanciata una BadRequestException → rispondo con 400
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsDTO handleBadRequest(BadRequestException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsDTO handleNotFound(NotFoundException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    // Questo metodo cattura TUTTI gli altri errori non gestiti sopra
    // Non voglio mai mostrare i dettagli tecnici dell'errore al client
    // ex.printStackTrace() stampa l'errore nella console per il developer
    // ma al client mando solo un messaggio generico
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsDTO handleGeneric(Exception ex) {
        ex.printStackTrace();
        return new ErrorsDTO("Errore interno del server, sto ancora imparando il back-end :(", LocalDateTime.now());
    }
}

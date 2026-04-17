package antoninopalazzolo.u2w2d5backend.services;

import antoninopalazzolo.u2w2d5backend.entities.Dipendente;
import antoninopalazzolo.u2w2d5backend.exceptions.BadRequestException;
import antoninopalazzolo.u2w2d5backend.payloads.DipendenteDTO;
import antoninopalazzolo.u2w2d5backend.repositories.DipendenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DipendenteService {
    private final DipendenteRepository dipendenteRepository;

    @Autowired // Constructor injection, potevo farea anche la field volendo ma è sempre meglio usare questa
    public DipendenteService(DipendenteRepository dipendenteRepository) {
        this.dipendenteRepository = dipendenteRepository;
    }

    public Dipendente saveDipendente(DipendenteDTO body) {
        if (this.dipendenteRepository.existsByEmail(body.email()))
            //controllo se esiste già un email uguale salvata
            throw new BadRequestException("L'indirizzo email" + body.email() + "è già in uso");
        //se esiste lancio l'eccezione
        if (this.dipendenteRepository.existsByUsername(body.username()))
            //stesa cosa vale per username, in quanto li avevo resi unique nelle entità
            throw new BadRequestException(("L'username" + body.username() + "è già in uso"));
        Dipendente newDipendente = new Dipendente(body.username(), body.name(), body.surname(), body.email());
        // se supera tutti i controlli, gli passo nella creazione
        //del nuovo dipendente, i valori del payload di dipedente
        //ovvero quelli che dobbiamo scrivere a mano su postman
        Dipendente dipendenteSalvato = this.dipendenteRepository.save(newDipendente);
        //uso il metodo save della repository per salvare il dipendente
        log.info("Il dipendente con id " + dipendenteSalvato.getId() + " è stato salvato correttamente!");
        //mi faccio un log per controllare, se tutto sia andato bene!
        return dipendenteSalvato;
        //mi ritorno il dipendente salvato!
    }
}

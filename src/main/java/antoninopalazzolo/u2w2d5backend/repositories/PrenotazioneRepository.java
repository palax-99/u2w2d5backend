package antoninopalazzolo.u2w2d5backend.repositories;

import antoninopalazzolo.u2w2d5backend.entities.Dipendente;
import antoninopalazzolo.u2w2d5backend.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    // Controllo se esiste già una prenotazione
    // per quel dipendente in quella data specifica
    // "un dipendente non può avere più prenotazioni nello stesso giorno"
    boolean existsByDipendenteAndDataRichiesta(Dipendente dipendente, LocalDate dataRichiesta);
}


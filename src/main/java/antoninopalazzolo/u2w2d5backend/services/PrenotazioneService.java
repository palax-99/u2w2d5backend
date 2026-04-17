package antoninopalazzolo.u2w2d5backend.services;

import antoninopalazzolo.u2w2d5backend.entities.Dipendente;
import antoninopalazzolo.u2w2d5backend.entities.Prenotazione;
import antoninopalazzolo.u2w2d5backend.entities.Viaggio;
import antoninopalazzolo.u2w2d5backend.exceptions.BadRequestException;
import antoninopalazzolo.u2w2d5backend.payloads.PrenotazioneDTO;
import antoninopalazzolo.u2w2d5backend.repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrenotazioneService {
    private final DipendenteService dipendenteService;
    private final ViaggioService viaggioService;
    private final PrenotazioneRepository prenotazioneRepository;

    @Autowired
    public PrenotazioneService(DipendenteService dipendenteService,
                               ViaggioService viaggioService,
                               PrenotazioneRepository prenotazioneRepository) {
        this.dipendenteService = dipendenteService;
        this.viaggioService = viaggioService;
        this.prenotazioneRepository = prenotazioneRepository;
    }

    public Prenotazione savePrenotazione(PrenotazioneDTO body) {
        Dipendente dipendente = this.dipendenteService.findByIdDipendente(body.dipendenteId());
        Viaggio viaggio = this.viaggioService.findById(body.viaggioId());
        // Ho la possibilità di cercare sia il dipendente che il viaggio con i metodi
        // perchè mi sono iniettato i loro service
        if (this.prenotazioneRepository.existsByDipendenteAndDataRichiesta(dipendente, body.dataRichiesta()))
            throw new BadRequestException("Il dipendente ha già una prenotazione per il " + body.dataRichiesta());
        //Controllo se esiste già unn dipendente che ha un aprenotazione per quella data, mamma mia che esercizio prof, lunghissimo ahahah
        Prenotazione nuovaPrenotazione = new Prenotazione(body.dataRichiesta(), body.note(), dipendente, viaggio);
        Prenotazione salvata = this.prenotazioneRepository.save(nuovaPrenotazione);
        log.info("Prenotazione con id " + salvata.getId() + " salvata correttamente!");
        return salvata;
    }
}

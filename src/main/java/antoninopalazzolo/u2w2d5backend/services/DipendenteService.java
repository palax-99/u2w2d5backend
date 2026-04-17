package antoninopalazzolo.u2w2d5backend.services;

import antoninopalazzolo.u2w2d5backend.entities.Dipendente;
import antoninopalazzolo.u2w2d5backend.exceptions.BadRequestException;
import antoninopalazzolo.u2w2d5backend.exceptions.NotFoundException;
import antoninopalazzolo.u2w2d5backend.payloads.DipendenteDTO;
import antoninopalazzolo.u2w2d5backend.repositories.DipendenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class DipendenteService {
    private final DipendenteRepository dipendenteRepository;
    private final Cloudinary cloudinaryConfig;

    @Autowired // Constructor injection, potevo farea anche la field volendo ma è sempre meglio usare questa
    public DipendenteService(DipendenteRepository dipendenteRepository, Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
        // aggiungo cloudinaryConfig nel costruttore
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

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        // Pageable è l'oggetto che contiene le istruzioni di paginazione
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        // findAll con il Pageable fa la query con paginazione automatica, che figata comunque!
        return this.dipendenteRepository.findAll(pageable);
    }

    public Dipendente findByIdDipendente(UUID id) {
        // orElseThrow cerca il dipendente per id
        // se non lo trova lancia automaticamente NotFoundException
        // se lo trova lo restituisce
        Dipendente found = this.dipendenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        log.info("Dipendente con id " + id + " trovato!");
        return found;
    }

    public Dipendente findByIdAndUpdate(UUID id, DipendenteDTO body) {
        // Trovo il dipendente, se non esiste lancio NotFoundException
        Dipendente found = this.findByIdDipendente(id);

        // Controllo email solo se sto cambiando email
        if (!found.getEmail().equals(body.email()) && this.dipendenteRepository.existsByEmail(body.email()))
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");

        // Controllo username solo se sto cambiando username
        if (!found.getUsername().equals(body.username()) && this.dipendenteRepository.existsByUsername(body.username()))
            throw new BadRequestException("L'username " + body.username() + " è già in uso!");

        // Aggiorno i campi
        found.setUsername(body.username());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setAvatar("https://ui-avatars.com/api?name=" + body.name() + "+" + body.surname());

        log.info("Dipendente con id " + id + " aggiornato!");
        return this.dipendenteRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        // Trovo il dipendente, se non esiste lancio NotFoundException
        Dipendente found = this.findByIdDipendente(id);
        this.dipendenteRepository.delete(found);
        log.info("Dipendente con id " + id + " eliminato!");
    }

    public Dipendente uploadAvatar(UUID id, MultipartFile file) throws IOException {
        // 1. Trovo il dipendente, se non esiste lancio NotFoundException
        Dipendente found = this.findByIdDipendente(id);

        // 2. Upload del file su Cloudinary
        // file.getBytes() converte il file in bytes — formato che Cloudinary accetta
        Map result = cloudinaryConfig.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // 3. Cloudinary ci restituisce una Map con varie info
        // "secure_url" è l'URL pubblico dell'immagine caricata
        String url = (String) result.get("secure_url");

        // 4. Aggiorno l'avatar del dipendente con l'URL di Cloudinary
        found.setAvatar(url);

        // 5. Salvo e ritorno il dipendente aggiornato
        log.info("Avatar del dipendente " + id + " aggiornato!");
        return this.dipendenteRepository.save(found);
    }
}

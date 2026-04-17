package antoninopalazzolo.u2w2d5backend.controllers;

import antoninopalazzolo.u2w2d5backend.entities.Prenotazione;
import antoninopalazzolo.u2w2d5backend.exceptions.BadRequestException;
import antoninopalazzolo.u2w2d5backend.payloads.PrenotazioneDTO;
import antoninopalazzolo.u2w2d5backend.services.PrenotazioneService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@RequestBody @Validated PrenotazioneDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return prenotazioneService.savePrenotazione(body);
    }

    @GetMapping
    public Page<Prenotazione> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return prenotazioneService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Prenotazione findById(@PathVariable UUID id) {
        return prenotazioneService.findById(id);
    }

    @PutMapping("/{id}")
    public Prenotazione findByIdAndUpdate(@PathVariable UUID id,
                                          @RequestBody @Validated PrenotazioneDTO body,
                                          BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return prenotazioneService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        prenotazioneService.findByIdAndDelete(id);
    }

}

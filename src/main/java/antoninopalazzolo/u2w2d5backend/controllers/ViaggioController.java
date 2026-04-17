package antoninopalazzolo.u2w2d5backend.controllers;

import antoninopalazzolo.u2w2d5backend.entities.Viaggio;
import antoninopalazzolo.u2w2d5backend.exceptions.BadRequestException;
import antoninopalazzolo.u2w2d5backend.payloads.ViaggioDTO;
import antoninopalazzolo.u2w2d5backend.services.ViaggioService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    private final ViaggioService viaggioService;

    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody @Validated ViaggioDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return viaggioService.save(body);
    }

    @GetMapping
    public Page<Viaggio> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return viaggioService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Viaggio findById(@PathVariable UUID id) {
        return viaggioService.findById(id);
    }

    @PutMapping("/{id}")
    public Viaggio findByIdAndUpdate(@PathVariable UUID id,
                                     @RequestBody @Validated ViaggioDTO body,
                                     BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return viaggioService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        viaggioService.findByIdAndDelete(id);
    }

    // Endpoint speciale per cambiare solo lo stato
    @PatchMapping("/{id}/stato")
    public Viaggio cambiaStato(@PathVariable UUID id, @RequestParam String stato) {
        return viaggioService.cambiaStato(id, stato);
    }
}

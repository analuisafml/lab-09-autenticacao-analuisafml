package br.ufpb.dcx.dsc.figurinhas.controller;

import br.ufpb.dcx.dsc.figurinhas.dto.FigurinhaDTO;
import br.ufpb.dcx.dsc.figurinhas.services.FigurinhaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/figurinhas")
public class FigurinhaController {

    private final FigurinhaService figurinhaService;

    public FigurinhaController(FigurinhaService figurinhaService) {
        this.figurinhaService = figurinhaService;
    }

    @GetMapping
    public List<FigurinhaDTO> getAllFigurinhas() {
        return figurinhaService.findAll();
    }

    @GetMapping("/{id}")
    public FigurinhaDTO getFigurinhaById(@PathVariable Long id) {
        return figurinhaService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Figurinha não encontrada"));
    }

    @GetMapping("/selecao/{selecao}")
    public List<FigurinhaDTO> getFigurinhasBySelecao(@PathVariable String selecao) {
        return figurinhaService.findBySelecao(selecao);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FigurinhaDTO createFigurinha(@Valid @RequestBody FigurinhaDTO figurinhaDTO) {
        return figurinhaService.save(figurinhaDTO);
    }

    @PutMapping("/{id}")
    public FigurinhaDTO updateFigurinha(@PathVariable Long id, @Valid @RequestBody FigurinhaDTO figurinhaDTO) {
        return figurinhaService.update(id, figurinhaDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Figurinha não encontrada"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFigurinha(@PathVariable Long id) {
        if (!figurinhaService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Figurinha não encontrada");
        }
    }
}
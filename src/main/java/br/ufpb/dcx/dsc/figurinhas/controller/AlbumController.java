package br.ufpb.dcx.dsc.figurinhas.controller;

import br.ufpb.dcx.dsc.figurinhas.dto.AlbumDTO;
import br.ufpb.dcx.dsc.figurinhas.dto.AlbumInputDTO;
import br.ufpb.dcx.dsc.figurinhas.services.AlbumService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<AlbumDTO> getAllAlbums() {
        return albumService.findAll();
    }

    @GetMapping("/{id}")
    public AlbumDTO getAlbumById(@PathVariable Long id) {
        // CORREÇÃO: A exceção já é lançada dentro do serviço se o álbum não for encontrado.
        return albumService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<AlbumDTO> getAlbumsByUserId(@PathVariable Long userId) {
        // CORREÇÃO: O serviço agora trata o caso de usuário não encontrado.
        return albumService.findByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDTO createAlbum(@PathVariable Long userId, @Valid @RequestBody AlbumInputDTO albumInputDTO) {
        // CORREÇÃO: Removido o try-catch. A exceção de usuário não encontrado será lançada pelo serviço.
        return albumService.save(userId, albumInputDTO);
    }

    @PutMapping("/{id}")
    public AlbumDTO updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumInputDTO albumInputDTO) {
        // CORREÇÃO: Removido o try-catch e o .orElseThrow.
        return albumService.update(id, albumInputDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Long id) {
        // CORREÇÃO: O método do serviço agora retorna void e lança exceção.
        albumService.deleteById(id);
    }

    @PostMapping("/{albumId}/figurinhas/{figurinhaId}")
    public AlbumDTO addFigurinhaToAlbum(@PathVariable Long albumId, @PathVariable Long figurinhaId) {
        // CORREÇÃO: Removido o try-catch. O serviço agora lida com todas as exceções (álbum/figurinha não encontrados, figurinha duplicada).
        return albumService.addFigurinhaToAlbum(albumId, figurinhaId);
    }

    @DeleteMapping("/{albumId}/figurinhas/{figurinhaId}")
    public AlbumDTO removeFigurinhaFromAlbum(@PathVariable Long albumId, @PathVariable Long figurinhaId) {
        // CORREÇÃO: Removido o try-catch. O serviço lida com as exceções.
        return albumService.removeFigurinhaFromAlbum(albumId, figurinhaId);
    }
}
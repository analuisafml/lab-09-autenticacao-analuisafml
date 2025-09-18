package br.ufpb.dcx.dsc.figurinhas.services;

import br.ufpb.dcx.dsc.figurinhas.dto.AlbumDTO;
import br.ufpb.dcx.dsc.figurinhas.dto.AlbumInputDTO;
import br.ufpb.dcx.dsc.figurinhas.dto.FigurinhaDTO;
import br.ufpb.dcx.dsc.figurinhas.exception.BusinessLogicException;
import br.ufpb.dcx.dsc.figurinhas.exception.ResourceNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.models.Album;
import br.ufpb.dcx.dsc.figurinhas.models.Figurinha;
import br.ufpb.dcx.dsc.figurinhas.models.User;
import br.ufpb.dcx.dsc.figurinhas.repository.AlbumRepository;
import br.ufpb.dcx.dsc.figurinhas.repository.FigurinhaRepository;
import br.ufpb.dcx.dsc.figurinhas.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final FigurinhaRepository figurinhaRepository;

    public AlbumService(AlbumRepository albumRepository, UserRepository userRepository, FigurinhaRepository figurinhaRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.figurinhaRepository = figurinhaRepository;
    }

    @Transactional(readOnly = true)
    public List<AlbumDTO> findAll() {
        return albumRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlbumDTO> findByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Não é possível listar álbuns pois o usuário com ID " + userId + " não foi encontrado.");
        }
        return albumRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlbumDTO findById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum com ID " + id + " não encontrado."));
        return mapToDTO(album);
    }

    @Transactional
    public AlbumDTO save(Long userId, AlbumInputDTO albumInputDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + userId + " não encontrado para associar ao novo álbum."));
        Album album = new Album();
        album.setNome(albumInputDTO.getNome());
        album.setUser(user);
        Album savedAlbum = albumRepository.save(album);
        return mapToDTO(savedAlbum);
    }

    @Transactional
    public AlbumDTO update(Long id, AlbumInputDTO albumInputDTO) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum com ID " + id + " não encontrado para atualização."));
        album.setNome(albumInputDTO.getNome());
        Album updatedAlbum = albumRepository.save(album);
        return mapToDTO(updatedAlbum);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Álbum com ID " + id + " não encontrado para exclusão.");
        }
        albumRepository.deleteById(id);
    }

    @Transactional
    public AlbumDTO addFigurinhaToAlbum(Long albumId, Long figurinhaId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum com ID " + albumId + " não encontrado."));
        Figurinha figurinha = figurinhaRepository.findById(figurinhaId)
                .orElseThrow(() -> new ResourceNotFoundException("Figurinha com ID " + figurinhaId + " não encontrada."));

        if (album.getFigurinhas().contains(figurinha)) {
            throw new BusinessLogicException("A figurinha com ID " + figurinhaId + " já existe no álbum.");
        }
        album.getFigurinhas().add(figurinha);
        Album updatedAlbum = albumRepository.save(album);
        return mapToDTO(updatedAlbum);
    }

    @Transactional
    public AlbumDTO removeFigurinhaFromAlbum(Long albumId, Long figurinhaId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum com ID " + albumId + " não encontrado."));
        Figurinha figurinha = figurinhaRepository.findById(figurinhaId)
                .orElseThrow(() -> new ResourceNotFoundException("Figurinha com ID " + figurinhaId + " não encontrada para remoção."));

        if (!album.getFigurinhas().contains(figurinha)) {
            throw new BusinessLogicException("A figurinha com ID " + figurinhaId + " não pertence a este álbum.");
        }
        album.getFigurinhas().remove(figurinha);
        Album updatedAlbum = albumRepository.save(album);
        return mapToDTO(updatedAlbum);
    }

    private AlbumDTO mapToDTO(Album album) {
        AlbumDTO dto = new AlbumDTO();
        dto.setAlbumId(album.getAlbumId());
        dto.setNome(album.getNome());

        List<FigurinhaDTO> figurinhasDTO = album.getFigurinhas().stream().map(fig -> {
            FigurinhaDTO figDTO = new FigurinhaDTO(fig.getNome(), fig.getSelecao());
            figDTO.setFigId(fig.getFigId());
            return figDTO;
        }).collect(Collectors.toList());

        dto.setFigurinhas(figurinhasDTO);
        return dto;
    }
}
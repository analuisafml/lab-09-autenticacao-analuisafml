package br.ufpb.dcx.dsc.figurinhas.services;

import br.ufpb.dcx.dsc.figurinhas.dto.FigurinhaDTO;
import br.ufpb.dcx.dsc.figurinhas.exception.ResourceNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.models.Figurinha;
import br.ufpb.dcx.dsc.figurinhas.repository.FigurinhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FigurinhaService {

    private final FigurinhaRepository figurinhaRepository;

    public FigurinhaService(FigurinhaRepository figurinhaRepository) {
        this.figurinhaRepository = figurinhaRepository;
    }

    @Transactional(readOnly = true)
    public List<FigurinhaDTO> findAll() {
        return figurinhaRepository.findAll().stream()
                .map(this::mapToDTO) // Supondo um método de conversão
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FigurinhaDTO findById(Long id) {
        Figurinha figurinha = figurinhaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Figurinha com ID " + id + " não encontrada."));
        return mapToDTO(figurinha);
    }

    @Transactional(readOnly = true)
    public List<FigurinhaDTO> findBySelecao(String selecao) {
        return figurinhaRepository.findBySelecaoContainingIgnoreCase(selecao).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FigurinhaDTO save(FigurinhaDTO figurinhaDTO) {
        Figurinha figurinha = new Figurinha();
        figurinha.setNome(figurinhaDTO.getNome());
        figurinha.setSelecao(figurinhaDTO.getSelecao());
        Figurinha savedFigurinha = figurinhaRepository.save(figurinha);
        return mapToDTO(savedFigurinha);
    }

    @Transactional
    public FigurinhaDTO update(Long id, FigurinhaDTO figurinhaDTO) {
        Figurinha figurinha = figurinhaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Figurinha com ID " + id + " não encontrada para atualização."));
        figurinha.setNome(figurinhaDTO.getNome());
        figurinha.setSelecao(figurinhaDTO.getSelecao());
        Figurinha updatedFigurinha = figurinhaRepository.save(figurinha);
        return mapToDTO(updatedFigurinha);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!figurinhaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Figurinha com ID " + id + " não encontrada para exclusão.");
        }
        figurinhaRepository.deleteById(id);
    }

    // Método auxiliar para mapear Entidade para DTO
    private FigurinhaDTO mapToDTO(Figurinha figurinha) {
        FigurinhaDTO dto = new FigurinhaDTO(figurinha.getNome(), figurinha.getSelecao());
        dto.setFigId(figurinha.getFigId());
        return dto;
    }
}
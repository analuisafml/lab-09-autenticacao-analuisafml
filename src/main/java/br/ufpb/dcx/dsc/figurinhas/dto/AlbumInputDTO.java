package br.ufpb.dcx.dsc.figurinhas.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AlbumInputDTO {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String nome;

    public AlbumInputDTO() {
    }

    public AlbumInputDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
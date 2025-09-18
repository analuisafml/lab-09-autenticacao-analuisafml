package br.ufpb.dcx.dsc.figurinhas.dto;

import java.util.List;

public class AlbumDTO {

    private Long albumId;
    private String nome;
    private Long userId;
    private List<FigurinhaDTO> figurinhas;

    public AlbumDTO() {
    }

    public AlbumDTO(String nome) {
        this.nome = nome;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<FigurinhaDTO> getFigurinhas() {
        return figurinhas;
    }

    public void setFigurinhas(List<FigurinhaDTO> figurinhas) {
        this.figurinhas = figurinhas;
    }
}

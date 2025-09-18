package br.ufpb.dcx.dsc.figurinhas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class UserDTO {

    private Long userId;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String nome;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    private PhotoDTO photo;

    private List<AlbumDTO> albums;

    public UserDTO() {
    }

    public UserDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }

    public List<AlbumDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumDTO> albums) {
        this.albums = albums;
    }
}
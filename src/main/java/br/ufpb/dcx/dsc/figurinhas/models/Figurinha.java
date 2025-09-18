package br.ufpb.dcx.dsc.figurinhas.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_figurinha")
public class Figurinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fig_id")
    private Long figId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "selecao")
    private String selecao;

    @ManyToMany(mappedBy = "figurinhas", fetch = FetchType.LAZY)
    private List<Album> albums;

    public Figurinha() {
    }

    public Figurinha(String nome, String selecao) {
        this.nome = nome;
        this.selecao = selecao;
    }

    public Long getFigId() {
        return figId;
    }

    public void setFigId(Long figId) {
        this.figId = figId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSelecao() {
        return selecao;
    }

    public void setSelecao(String selecao) {
        this.selecao = selecao;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}

package br.ufpb.dcx.dsc.figurinhas.dto;

import br.ufpb.dcx.dsc.figurinhas.validation.ValidSelecao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FigurinhaDTO {

    private Long figId;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String nome;

    @NotNull
    @NotEmpty
    @ValidSelecao
    private String selecao;

    public FigurinhaDTO() {
    }

    public FigurinhaDTO(String nome, String selecao) {
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
}
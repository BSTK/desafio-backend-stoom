package dev.bstk.stoom.endereco.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TB_ENDERECO")
public class Endereco implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    @Column(name = "RUA")
    private String rua;

    @NotNull @NotEmpty
    @Column(name = "NUMERO")
    private String numero;

    @NotNull @NotEmpty
    @Column(name = "COMPLEMENTO")
    private String complemento;

    @NotNull @NotEmpty
    @Column(name = "BAIRRO")
    private String bairro;

    @NotNull @NotEmpty
    @Column(name = "CIDADE")
    private String cidade;

    @NotNull @NotEmpty
    @Column(name = "ESTADO")
    private String estado;

    @NotNull @NotEmpty
    @Column(name = "PAIS")
    private String pais;

    @NotNull @NotEmpty
    @Column(name = "CEP")
    private String cep;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;
}

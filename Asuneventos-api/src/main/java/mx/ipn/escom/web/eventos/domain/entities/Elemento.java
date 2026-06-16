package mx.ipn.escom.web.eventos.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "elemento")
public class Elemento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_elemento")
    private Long idElemento;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private Plan plan;

    @Column(nullable = false)
    private Integer orden;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Integer duracion;
}

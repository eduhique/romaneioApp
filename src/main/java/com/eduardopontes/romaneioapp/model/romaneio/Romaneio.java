package com.eduardopontes.romaneioapp.model.romaneio;

import com.eduardopontes.romaneioapp.model.order.Order;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Romaneio implements Serializable {

    private static final long serialVersionUID = -4329272826656812251L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "romaneio_sequence")
    @SequenceGenerator(name = "romaneio_sequence", allocationSize = 1)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    @Size(max = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    private RomaneioStatus orderStatus;

    private LocalDateTime statusDate;

    @OneToMany(mappedBy = "romaneio", fetch = FetchType.LAZY)
    private Set<Order> orderItems;

    @NotNull
    @Column(nullable = false)
    private boolean Active;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    private String comments;

}

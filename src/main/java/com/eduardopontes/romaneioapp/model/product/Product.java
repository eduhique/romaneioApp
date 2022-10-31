package com.eduardopontes.romaneioapp.model.product;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product implements Serializable {

    private static final long serialVersionUID = -1541490763464438620L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", allocationSize = 1)
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = 150)
    @Size(max = 150)
    private String name;

    @NotNull
    @Column(nullable = false)
    private boolean active;

    @NotNull
    @OneToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}

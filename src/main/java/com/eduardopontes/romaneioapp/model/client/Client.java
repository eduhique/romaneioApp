package com.eduardopontes.romaneioapp.model.client;

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
public class Client implements Serializable {

    private static final long serialVersionUID = 1654548526768727237L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
    @SequenceGenerator(name = "client_sequence", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "O nome do Cliente não deve estar nulo ou vazio.")
    @Column(nullable = false)
    private String name;

    @Column(length = 100)
    @Size(max = 100)
    private String district;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<Order> orders;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo do cliente não pode ser nulo ou vazio.")
    private ClientType clientType;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    private String comments;
}

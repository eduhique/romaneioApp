package com.eduardopontes.romaneioapp.model.order;

import com.eduardopontes.romaneioapp.model.client.Client;
import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import com.eduardopontes.romaneioapp.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "ro_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 459931777844269555L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "romaneio_id", nullable = false)
    private Romaneio romaneio;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "users_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    private OrderStatus status;

    private LocalDateTime statusDate;
}

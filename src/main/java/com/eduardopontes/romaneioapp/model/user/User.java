package com.eduardopontes.romaneioapp.model.user;

import com.eduardopontes.romaneioapp.model.order.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ro_user")
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 3730043526030942827L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", allocationSize = 1)
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = 150)
    @Size(max = 150)
    private String name;

    @NotEmpty
    @Column(nullable = false, length = 50, unique = true, updatable = false)
    @Size(max = 50)
    private String nickname;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Column(nullable = false)
    @Size(min = 6)
    private String password;

    @NotNull
    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Order> orders;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Function function;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}

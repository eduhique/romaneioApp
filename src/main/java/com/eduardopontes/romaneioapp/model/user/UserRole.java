package com.eduardopontes.romaneioapp.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -3380705379181467804L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_sequence")
    @SequenceGenerator(name = "user_role_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @OneToOne
    @NotNull
    private Role role;
}

package com.eduardopontes.romaneioapp.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Role implements Serializable {

    private static final long serialVersionUID = -8892397892193348590L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence", allocationSize = 1)
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true, length = 100)
    @Size(max = 100)
    private String roleName;

}

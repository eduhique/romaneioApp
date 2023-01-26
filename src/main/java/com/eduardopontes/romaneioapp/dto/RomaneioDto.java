package com.eduardopontes.romaneioapp.dto;

import com.eduardopontes.romaneioapp.model.romaneio.RomaneioStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class RomaneioDto implements Serializable {

    private static final long serialVersionUID = 4100440129914373632L;

    private Long id;

    private String name;

    private RomaneioStatus status;

    private LocalDateTime statusDate;

    private Boolean active;


    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;

    private String comments;

}

package com.eduardopontes.romaneioapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserResumeDto implements Serializable {


    private static final long serialVersionUID = 3156948764815904438L;

    private Long id;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String nickname;

    @NotNull
    private String function;

    private Date createdDate;
}

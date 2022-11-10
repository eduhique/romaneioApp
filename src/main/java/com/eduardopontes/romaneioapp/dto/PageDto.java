package com.eduardopontes.romaneioapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class PageDto<T> implements Serializable {

    private static final long serialVersionUID = -5778661790905762985L;

    private List<T> data;

    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private long totalElements;

    private boolean last;
}

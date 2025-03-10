package com.pranjal.dto;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private List<NotesDto> notes;

    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private Boolean isFirst;
    private Boolean isLast;


}

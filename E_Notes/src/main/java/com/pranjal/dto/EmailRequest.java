package com.pranjal.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FavouriteNoteDto {
    private Integer id;
    private NotesDto notes;
    private Integer userId;
}

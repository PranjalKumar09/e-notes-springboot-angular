package com.pranjal.repository;

import com.pranjal.dto.FavouriteNoteDto;
import com.pranjal.enitity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote, Integer> {

    List<FavouriteNote> findByUserId(Integer userId);
}

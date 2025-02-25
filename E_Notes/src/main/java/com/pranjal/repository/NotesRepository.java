package com.pranjal.repository;

import com.pranjal.dto.NotesDto;
import com.pranjal.enitity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {



    Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);
}

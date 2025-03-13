package com.pranjal.schedular;


import com.pranjal.enitity.Notes;
import com.pranjal.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {

    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesScheduler(){
        LocalDateTime cutoffDate = LocalDateTime.now().minusMinutes(5);
        List<Notes> deleteNotes = notesRepository.findByIsDeletedAndDeletedAtBefore(true, cutoffDate);
        notesRepository.deleteAll(deleteNotes);
    }
}

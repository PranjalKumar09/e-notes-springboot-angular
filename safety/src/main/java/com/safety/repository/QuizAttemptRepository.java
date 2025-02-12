package com.safety.repository;


import com.safety.enitity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAttemptRepository extends JpaRepository <QuizAttempt, Integer> {
}

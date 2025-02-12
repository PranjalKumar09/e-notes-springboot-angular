package com.safety.repository;

import com.safety.enitity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Integer> {
}

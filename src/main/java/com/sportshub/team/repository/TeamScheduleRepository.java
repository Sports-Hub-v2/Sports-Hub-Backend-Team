package com.sportshub.team.repository;

import com.sportshub.team.domain.TeamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamScheduleRepository extends JpaRepository<TeamSchedule, Long> {
    List<TeamSchedule> findByTeamId(Long teamId);
    List<TeamSchedule> findByMatchId(Long matchId);
}

package com.sportshub.team.repository;

import com.sportshub.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByRegionIgnoreCase(String region);
    Optional<Team> findByTeamName(String teamName);
}


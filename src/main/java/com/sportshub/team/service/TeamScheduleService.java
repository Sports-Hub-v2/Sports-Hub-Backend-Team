package com.sportshub.team.service;

import com.sportshub.team.domain.TeamSchedule;
import com.sportshub.team.repository.TeamScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamScheduleService {
    private final TeamScheduleRepository scheduleRepository;

    @Transactional
    public TeamSchedule addSchedule(Long teamId, Long matchId, String eventType) {
        TeamSchedule schedule = new TeamSchedule();
        schedule.setTeamId(teamId);
        schedule.setMatchId(matchId);
        schedule.setEventType(eventType != null ? eventType : "MATCH");

        TeamSchedule saved = scheduleRepository.save(schedule);
        log.info("Added schedule {} for team {} and match {}", saved.getId(), teamId, matchId);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<TeamSchedule> getTeamSchedules(Long teamId) {
        return scheduleRepository.findByTeamId(teamId);
    }

    @Transactional(readOnly = true)
    public List<TeamSchedule> getMatchSchedules(Long matchId) {
        return scheduleRepository.findByMatchId(matchId);
    }
}

package com.sportshub.team.web;

import com.sportshub.team.domain.Team;
import com.sportshub.team.domain.TeamSchedule;
import com.sportshub.team.service.TeamService;
import com.sportshub.team.service.TeamScheduleService;
import com.sportshub.team.web.dto.TeamDtos.CreateRequest;
import com.sportshub.team.web.dto.TeamDtos.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Team create(@Validated @RequestBody CreateRequest req) {
        Team t = new Team();
        BeanUtils.copyProperties(req, t);

        // name 필드가 있으면 teamName으로 설정 (프론트엔드 호환성)
        if (req.getName() != null && !req.getName().isEmpty()) {
            t.setTeamName(req.getName());
        }

        // created_at이 null이면 현재 시간으로 설정
        if (t.getCreatedAt() == null) {
            t.setCreatedAt(java.time.LocalDateTime.now());
        }

        return teamService.create(t);
    }

    @GetMapping("/{id}")
    public Team get(@PathVariable Long id) {
        return teamService.get(id);
    }

    @PatchMapping("/{id}")
    public Team update(@PathVariable Long id, @RequestBody UpdateRequest req) {
        Team patch = new Team();
        BeanUtils.copyProperties(req, patch);
        return teamService.update(id, patch);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teamService.delete(id);
    }

    @GetMapping
    public List<Team> list(@RequestParam(required = false) String region) {
        return teamService.list(region);
    }

    @PostMapping("/{id}/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamSchedule addSchedule(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Long matchId = ((Number) request.get("matchId")).longValue();
        String eventType = (String) request.getOrDefault("eventType", "MATCH");
        return scheduleService.addSchedule(id, matchId, eventType);
    }

    @GetMapping("/{id}/schedules")
    public List<TeamSchedule> getSchedules(@PathVariable Long id) {
        return scheduleService.getTeamSchedules(id);
    }
}


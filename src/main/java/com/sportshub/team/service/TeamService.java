package com.sportshub.team.service;

import com.sportshub.team.domain.Team;
import com.sportshub.team.domain.TeamMembership;
import com.sportshub.team.domain.TeamMembershipId;
import com.sportshub.team.repository.TeamRepository;
import com.sportshub.team.repository.TeamMembershipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMembershipRepository membershipRepository;

    @Transactional
    public Team create(Team t) {
        try {
            return teamRepository.save(t);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "duplicate team name");
        }
    }

    @Transactional(readOnly = true)
    public Team get(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "team not found"));
    }

    @Transactional
    public Team update(Long id, Team patch) {
        Team t = get(id);
        if (patch.getTeamName() != null) t.setTeamName(patch.getTeamName());
        if (patch.getRegion() != null) t.setRegion(patch.getRegion());
        if (patch.getCaptainProfileId() != null) t.setCaptainProfileId(patch.getCaptainProfileId());
        if (patch.getRivalTeams() != null) t.setRivalTeams(patch.getRivalTeams());
        if (patch.getDescription() != null) t.setDescription(patch.getDescription());
        if (patch.getMaxMembers() != null) t.setMaxMembers(patch.getMaxMembers());
        if (patch.getAgeGroup() != null) t.setAgeGroup(patch.getAgeGroup());
        if (patch.getSkillLevel() != null) t.setSkillLevel(patch.getSkillLevel());
        if (patch.getActivityType() != null) t.setActivityType(patch.getActivityType());
        if (patch.getLogoUrl() != null) t.setLogoUrl(patch.getLogoUrl());
        if (patch.getHomeGround() != null) t.setHomeGround(patch.getHomeGround());
        try {
            return teamRepository.save(t);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "duplicate team name");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "team not found");
        }
        teamRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Team> list(String region) {
        if (region == null || region.isBlank()) return teamRepository.findAll();
        return teamRepository.findByRegionIgnoreCase(region);
    }

    /**
     * test1234 사용자를 위한 팀 초기화 메서드
     * - Thunder Strikers: test1234가 팀장인 팀
     * - Seoul Warriors: test1234가 멤버인 팀
     */
    @PostConstruct
    @Transactional
    public void initializeTest1234Teams() {
        Long test1234ProfileId = 2L; // test1234 사용자의 프로필 ID (가정)
        Long seoulWarriorsCaptainId = 3L; // Seoul Warriors 팀장의 프로필 ID

        try {
            // Thunder Strikers 팀 생성 (test1234가 팀장)
            Team thunderStrikers = createTeamIfNotExists(
                "Thunder Strikers",
                test1234ProfileId,
                "서울 마포구",
                "test1234가 이끄는 강력한 공격진",
                22,
                "20-35",
                "ADVANCED",
                "COMPETITIVE"
            );

            // Seoul Warriors 팀 생성 (test1234가 멤버)
            Team seoulWarriors = createTeamIfNotExists(
                "Seoul Warriors", 
                seoulWarriorsCaptainId,
                "서울 용산구",
                "서울을 대표하는 전사들의 모임",
                20,
                "25-40",
                "INTERMEDIATE",
                "REGULAR"
            );

            // test1234를 Thunder Strikers의 팀장으로 추가
            createMembershipIfNotExists(thunderStrikers.getId(), test1234ProfileId, "CAPTAIN");

            // Seoul Warriors 팀장 멤버십 추가
            createMembershipIfNotExists(seoulWarriors.getId(), seoulWarriorsCaptainId, "CAPTAIN");

            // test1234를 Seoul Warriors의 멤버로 추가
            createMembershipIfNotExists(seoulWarriors.getId(), test1234ProfileId, "MEMBER");

            // 추가 더미 멤버들 추가
            addDummyMembers(thunderStrikers.getId(), seoulWarriors.getId());

            log.info("test1234 사용자의 팀 초기화가 완료되었습니다.");
            log.info("- Thunder Strikers (팀장): {}", thunderStrikers.getId());
            log.info("- Seoul Warriors (멤버): {}", seoulWarriors.getId());

        } catch (Exception e) {
            log.warn("팀 초기화 중 오류 발생 (이미 존재할 수 있음): {}", e.getMessage());
        }
    }

    private Team createTeamIfNotExists(String teamName, Long captainId, String region, 
                                      String description, Integer maxMembers, String ageGroup,
                                      String skillLevel, String activityType) {
        return teamRepository.findByTeamName(teamName)
            .orElseGet(() -> {
                Team team = new Team();
                team.setTeamName(teamName);
                team.setCaptainProfileId(captainId);
                team.setRegion(region);
                team.setDescription(description);
                team.setMaxMembers(maxMembers);
                team.setAgeGroup(ageGroup);
                team.setSkillLevel(skillLevel);
                team.setActivityType(activityType);
                team.setCreatedAt(LocalDateTime.now());
                return teamRepository.save(team);
            });
    }

    private void createMembershipIfNotExists(Long teamId, Long profileId, String role) {
        TeamMembershipId membershipId = new TeamMembershipId(teamId, profileId);
        if (!membershipRepository.existsById(membershipId)) {
            TeamMembership membership = new TeamMembership();
            membership.setId(membershipId);
            membership.setRoleInTeam(role);
            membership.setIsActive(true);
            membership.setJoinedAt(LocalDateTime.now());
            membershipRepository.save(membership);
        }
    }

    private void addDummyMembers(Long thunderStrikersId, Long seoulWarriorsId) {
        // Thunder Strikers 추가 멤버들
        createMembershipIfNotExists(thunderStrikersId, 4L, "MEMBER");
        createMembershipIfNotExists(thunderStrikersId, 5L, "MEMBER");
        createMembershipIfNotExists(thunderStrikersId, 6L, "VICE_CAPTAIN");

        // Seoul Warriors 추가 멤버들
        createMembershipIfNotExists(seoulWarriorsId, 7L, "MEMBER");
        createMembershipIfNotExists(seoulWarriorsId, 8L, "MEMBER");
        createMembershipIfNotExists(seoulWarriorsId, 9L, "VICE_CAPTAIN");
    }
}


package com.sportshub.team.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;

    @Column(name = "captain_profile_id")
    private Long captainProfileId;

    private String region;
    
    private String description;
    
    @Column(name = "max_members")
    private Integer maxMembers;
    
    @Column(name = "age_group")
    private String ageGroup;
    
    @Column(name = "skill_level")
    private String skillLevel;
    
    @Column(name = "activity_type")
    private String activityType;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(name = "home_ground")
    private String homeGround;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "rival_teams")
    private String rivalTeams;
}


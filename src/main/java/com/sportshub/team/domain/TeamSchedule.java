package com.sportshub.team.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_schedules")
@Getter
@Setter
@NoArgsConstructor
public class TeamSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Column(name = "event_type", length = 50)
    private String eventType = "MATCH";

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}

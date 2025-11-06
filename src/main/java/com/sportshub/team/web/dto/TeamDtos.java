package com.sportshub.team.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamDtos {
    @Data
    public static class CreateRequest {
        @NotBlank
        private String name;  // 프론트엔드와 맞춤
        private String teamName; // 기존 호환성을 위해 유지
        private Long captainProfileId;
        private String region;
        private String subRegion;
        private String description;
        private String logoUrl;
        private String homeGround;
        private String rivalTeams;
        private Integer maxMembers;
        private String ageGroup;
        private String skillLevel;
        private String activityType;
    }

    @Data
    public static class UpdateRequest {
        private String teamName;
        private Long captainProfileId;
        private String region;
        private String rivalTeams;
        private String description;
        private Integer maxMembers;
        private String ageGroup;
        private String skillLevel;
        private String activityType;
        private String logoUrl;
        private String homeGround;
    }
}


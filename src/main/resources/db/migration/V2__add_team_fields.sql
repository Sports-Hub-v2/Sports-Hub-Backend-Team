-- Add new fields to teams table for enhanced team information

ALTER TABLE teams
ADD COLUMN description TEXT,
ADD COLUMN max_members INT,
ADD COLUMN age_group VARCHAR(50),
ADD COLUMN skill_level VARCHAR(50),
ADD COLUMN activity_type VARCHAR(50),
ADD COLUMN logo_url VARCHAR(255),
ADD COLUMN home_ground VARCHAR(255);

-- Add some sample data for testing
INSERT INTO teams (team_name, captain_profile_id, region, description, max_members, age_group, skill_level, activity_type) VALUES
('FC 번개', 1, '서울 강남구', '조기축구를 사랑하는 열정적인 팀', 20, '20-40', 'INTERMEDIATE', 'REGULAR'),
('조기축구 레전드', 1, '서울 서초구', '경험이 풍부한 조기축구 동호회', 25, '30-50', 'ADVANCED', 'REGULAR'),
('주말 워리어즈', 1, '경기 성남시', '주말마다 경기하는 워리어들', 18, '25-45', 'INTERMEDIATE', 'WEEKEND');

-- Add memberships for the test user (profile_id = 1)
INSERT INTO team_memberships (team_id, profile_id, role_in_team) VALUES
(1, 1, 'CAPTAIN'),
(2, 1, 'MEMBER'),
(3, 1, 'MEMBER');


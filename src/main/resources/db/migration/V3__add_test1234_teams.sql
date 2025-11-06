-- Add teams for test1234 user account
-- Assuming test1234 user has profile_id = 2 (adjust as needed)

-- Team 1: test1234가 팀장인 팀
INSERT INTO teams (team_name, captain_profile_id, region, description, max_members, age_group, skill_level, activity_type) VALUES
('Thunder Strikers', 2, '서울 마포구', 'test1234가 이끄는 강력한 공격진', 22, '20-35', 'ADVANCED', 'COMPETITIVE');

-- Team 2: test1234가 멤버인 팀
INSERT INTO teams (team_name, captain_profile_id, region, description, max_members, age_group, skill_level, activity_type) VALUES
('Seoul Warriors', 3, '서울 용산구', '서울을 대표하는 전사들의 모임', 20, '25-40', 'INTERMEDIATE', 'REGULAR');

-- test1234 사용자를 Thunder Strikers의 팀장으로 설정
INSERT INTO team_memberships (team_id, profile_id, role_in_team) VALUES
((SELECT id FROM teams WHERE team_name = 'Thunder Strikers'), 2, 'CAPTAIN');

-- test1234 사용자를 Seoul Warriors의 멤버로 설정
INSERT INTO team_memberships (team_id, profile_id, role_in_team) VALUES
((SELECT id FROM teams WHERE team_name = 'Seoul Warriors'), 2, 'MEMBER');

-- Seoul Warriors의 팀장을 위한 더미 사용자 (profile_id = 3) 멤버십 추가
INSERT INTO team_memberships (team_id, profile_id, role_in_team) VALUES
((SELECT id FROM teams WHERE team_name = 'Seoul Warriors'), 3, 'CAPTAIN');

-- 추가 멤버들을 각 팀에 추가 (더미 데이터)
-- Thunder Strikers 추가 멤버들
INSERT INTO team_memberships (team_id, profile_id, role_in_team) VALUES
((SELECT id FROM teams WHERE team_name = 'Thunder Strikers'), 4, 'MEMBER'),
((SELECT id FROM teams WHERE team_name = 'Thunder Strikers'), 5, 'MEMBER'),
((SELECT id FROM teams WHERE team_name = 'Thunder Strikers'), 6, 'VICE_CAPTAIN');

-- Seoul Warriors 추가 멤버들
INSERT INTO team_memberships (team_id, profile_id, role_in_team) VALUES
((SELECT id FROM teams WHERE team_name = 'Seoul Warriors'), 7, 'MEMBER'),
((SELECT id FROM teams WHERE team_name = 'Seoul Warriors'), 8, 'MEMBER'),
((SELECT id FROM teams WHERE team_name = 'Seoul Warriors'), 9, 'VICE_CAPTAIN');

-- 팀 공지사항 추가
INSERT INTO team_notices (team_id, title, content) VALUES
((SELECT id FROM teams WHERE team_name = 'Thunder Strikers'), '팀 창설 공지', 'Thunder Strikers 팀이 공식적으로 창설되었습니다! 모든 멤버들의 적극적인 참여 부탁드립니다.'),
((SELECT id FROM teams WHERE team_name = 'Thunder Strikers'), '첫 번째 훈련 일정', '이번 주말 토요일 오전 10시, 마포구 월드컵경기장 보조구장에서 첫 훈련을 시작합니다.'),
((SELECT id FROM teams WHERE team_name = 'Seoul Warriors'), '새 멤버 환영', 'test1234님을 Seoul Warriors의 새로운 멤버로 환영합니다!'),
((SELECT id FROM teams WHERE team_name = 'Seoul Warriors'), '정기 모임 안내', '매주 일요일 오후 2시, 용산구 효창공원에서 정기 모임을 갖습니다.');

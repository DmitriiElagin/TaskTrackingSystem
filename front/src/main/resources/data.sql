INSERT INTO "SYS_USER" (user_id, username, password, role, first_name,
                        last_name, enabled, expired, locked, credentials_expired, icon_name)
VALUES (10, 'Admin', '$2a$10$AOixfABYET98OoOfYH.RWePuIPNPBK7uv26E0QUB5JMCsaack/BDC', 'ROLE_ADMINISTRATOR', 'Дмитрий',
        'Елагин', true, false, false, false, 'USER'),
       (20, 'User', '$2a$10$ZfiX6TwaFj7QAlHpPEiMWuhdizQdHkZCSsTz3y0O/ABsE1fvskKC2', 'ROLE_USER', 'Иван', 'Иванов', true,
        false, false, false, 'USER');
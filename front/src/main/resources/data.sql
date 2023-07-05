INSERT INTO "SYS_USER" (user_id, username, password, role, first_name,
                        last_name, enabled, expired, locked, credentials_expired)
VALUES (10, 'Admin', '$2a$10$AOixfABYET98OoOfYH.RWePuIPNPBK7uv26E0QUB5JMCsaack/BDC', 'ROLE_ADMINISTRATOR', 'Дмитрий',
        'Елагин', true, false, false, false),
       (20, 'User', '$2y$10$pBtATLgXj8mS4Jv3MYpIYOFCYDfvxW8fMNrevdAbi//5Mpg.rOvfe', 'ROLE_USER', 'Иван', 'Иванов', true,
        false, false, false);
-- Pa$$word123
-- 1234
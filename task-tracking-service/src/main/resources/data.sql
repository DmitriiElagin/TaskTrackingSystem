INSERT INTO "PROJECT" (id, title)
VALUES (10, 'Test project'),
       (20, 'Shufflegate');

INSERT INTO "_USER" (id, first_name, last_name)
VALUES (10, 'Дмитрий', 'Елагин'),
       (20, 'Иван', 'Иванов');

INSERT INTO "TASK" (id, theme, type, project_id, user_id, priority, description)
VALUES (10, 'О разработке сервиса задач', 'Разработка', 10, 10, 1, 'Разработать сервис для обработки запросов задач'),
       (20, 'Об исправлении ошибки в обработки запроса', 'Баг', 20, 20, 2,
        'Исправить ошибку маппинга при обработке запроса');
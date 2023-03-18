INSERT INTO offered_services (id, name, price, deleted, date_created)
VALUES (1, 'Първичен преглед', 60, false, (now() at time zone 'utc')),
       (2, 'Вторичен преглед в рамките на едно заболяване', 30.00, false, (now() at time zone 'utc')),
       (3, 'Консултация по документи', 30.00, false, (now() at time zone 'utc')),
       (4, 'Онлайн консултация', 30.00, false, (now() at time zone 'utc')),
       (5, 'Домашно посещение в рамките на гр. Плевен', 80.00, false, (now() at time zone 'utc')),
       (6, 'Вземане на венозна кръв и поставяне на абокат', 20.00, false, (now() at time zone 'utc')),
       (7, 'Поставяне на мускулна инжекция', 10.00, false, (now() at time zone 'utc')),
       (8, 'Измерване на АН (кръвно налягане)', 5.00, false, (now() at time zone 'utc')),
       (9, 'Вземане на периферен секрет за микробиологично изследване', 10.00, false, (now() at time zone 'utc')),
       (10, 'Стомашна промивка', 25.00, false, (now() at time zone 'utc')),
       (11, 'Клизма', 15.00, false, (now() at time zone 'utc')),
       (12, 'Инхалация на медикамент', 10.00, false, (now() at time zone 'utc')),
       (13, 'Първична обработка на рана и поставяне на тетанус', 25.00, false, (now() at time zone 'utc'));

INSERT INTO roles (id, name, deleted, date_created)
VALUES ('83f00c4a-3e7a-4f74-b8d7-04705dad0be1', 'ROLE_DOCTOR', false, (now() at time zone 'utc')),
       ('e02fe4eb-a24b-4588-a48c-197bd00138a2', 'ROLE_PATIENT', false, (now() at time zone 'utc')),
       ('e02fe4eb-a24b-4588-a48c-197bd00138a3', 'ROLE_PARENT', false, (now() at time zone 'utc')),
       ('107d12ee-6469-42ae-9439-db1d377b61a5', 'ROLE_ADMINISTRATOR', false, (now() at time zone 'utc'));

INSERT INTO event_data(id, hours, intervals)
VALUES  (1, '08:00', 5),
        (2, '08:30', 10),
        (3, '09:00', 15),
        (4, '09:30', 20),
        (5, '10:00', 25),
        (6, '10:30', 30),
        (7, '11:00', 35),
        (8, '11:30', 40),
        (9, '12:00', 45),
        (10, '12:30', 50),
        (11, '13:00', 55),
        (12, '13:30', 60),
        (13, '14:00', 65),
        (14, '14:30', 70),
        (15, '15:00', 75),
        (16, '15:30', 80),
        (17, '16:00', 85),
        (18, '16:30', 90),
        (19, '17:00', 95),
        (20, '17:30', 100),
        (21, '18:00', 105),
        (22, '18:30', 110),
        (23, '19:00', 115),
        (24, '19:30', 120),
        (25, '20:00', 125);

INSERT INTO schedules(id, deleted, date_created)
VALUES ('dd7e7a79-18fe-46f3-9ffe-29aef8c18ed6', false, (now() at time zone 'utc')),
        ('6bba2627-3f52-43f4-9c28-5ab3d7ce6151', false, (now() at time zone 'utc'));

INSERT INTO cabinets(id, name, city, address, post_code, mobile_phone, deleted, date_created, schedule_id)
VALUES (1, 'Габрово', 'гр. Габрово', 'Медицински център Хипократ, площад Белорусия 2', '5300', '123456789', false, (now() at time zone 'utc'), 'dd7e7a79-18fe-46f3-9ffe-29aef8c18ed6'),
        (2, 'Плевен', 'гр. Плевен', 'ул. Стефан Караджа 49', '5800', '123456789', false, (now() at time zone 'utc'), '6bba2627-3f52-43f4-9c28-5ab3d7ce6151');
INSERT INTO offered_services (name, price, deleted, date_created)
VALUES ('Първичен преглед', 60, false, (now() at time zone 'utc')),
       ('Вторичен преглед в рамките на едно заболяване', 30.00, false, (now() at time zone 'utc')),
       ('Консултация по документи', 30.00, false, (now() at time zone 'utc')),
       ('Онлайн консултация', 30.00, false, (now() at time zone 'utc')),
       ('Домашно посещение в рамките на гр. Плевен', 80.00, false, (now() at time zone 'utc')),
       ('Вземане на венозна кръв и поставяне на абокат', 20.00, false, (now() at time zone 'utc')),
       ('Поставяне на мускулна инжекция', 10.00, false, (now() at time zone 'utc')),
       ('Измерване на АН (кръвно налягане)', 5.00, false, (now() at time zone 'utc')),
       ('Вземане на периферен секрет за микробиологично изследване', 10.00, false, (now() at time zone 'utc')),
       ('Стомашна промивка', 25.00, false, (now() at time zone 'utc')),
       ('Клизма', 15.00, false, (now() at time zone 'utc')),
       ('Инхалация на медикамент', 10.00, false, (now() at time zone 'utc')),
       ('Първична обработка на рана и поставяне на тетанус', 25.00, false, (now() at time zone 'utc'));

INSERT INTO roles (id, name, deleted, date_created)
VALUES ('83f00c4a-3e7a-4f74-b8d7-04705dad0be1', 'ROLE_DOCTOR', false, (now() at time zone 'utc')),
       ('e02fe4eb-a24b-4588-a48c-197bd00138a2', 'ROLE_PATIENT', false, (now() at time zone 'utc')),
       ('e02fe4eb-a24b-4588-a48c-197bd00138a3', 'ROLE_PARENT', false, (now() at time zone 'utc')),
       ('107d12ee-6469-42ae-9439-db1d377b61a5', 'ROLE_ADMINISTRATOR', false, (now() at time zone 'utc'));

INSERT INTO event_data(hours, intervals)
VALUES  ('08:00', 5),
        ('08:30', 10),
        ('09:00', 15),
        ('09:30', 20),
        ('10:00', 25),
        ('10:30', 30),
        ('11:00', 35),
        ('11:30', 40),
        ('12:00', 45),
        ('12:30', 50),
        ('13:00', 55),
        ('13:30', 60),
        ('14:00', 65),
        ('14:30', 70),
        ('15:00', 75),
        ('15:30', 80),
        ('16:00', 85),
        ('16:30', 90),
        ('17:00', 95),
        ('17:30', 100),
        ('18:00', 105),
        ('18:30', 110),
        ('19:00', 115),
        ('19:30', 120),
        ('20:00', 125);

INSERT INTO cabinets(name, city, address, post_code, phone_number)
VALUES ()
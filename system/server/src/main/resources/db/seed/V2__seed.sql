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

INSERT INTO application_users(id, email, password, first_name, last_name, phone_number, salt)
VALUES ('e02fe4eb-a24b-4588-a48c-197bd00138a7', 'pediamedbg@gmail.com', '$2a$10$wtU17elEdaV7cBHqYv6Q6ukRwhFDj1NhVazrRrRmv/hJ.ZmlMUqY6', 'Павлина', 'Михова', '0878787342', '1f9119cc-1215-44f7-b1b7-86201973211e');

INSERT INTO doctors (id, years_of_experience, age, birth_date, biography, application_user_id)
VALUES ('83f00c4a-3e7a-4f74-b8d7-04705dad0ba2', 25, 49, '13.01.1973', 'Д-р Павлина Петрова Михова е специалист педиатър и неонатолог в Плевен и Габрово с над 20 години опит. Квалифициран специалист е в областта на трансфонтанелната и абдоминалната ехография, води детска консултация, провежда имунизации спрямо имунизациония календар на България, както и препоръчителни ваксинации. От 1998 г. до 2003 г. д-р Петрова работи като педиатър в Център за спешна медицинска помощ Габрово. От 2003 г. до 2005 г. е ординатор в Отделение по Неонатология към УМБАЛ Георги Странски - Плевен. От 2005 г. до 2012 г. е ординатор в Отделение по Неонатология към МБАЛ Д-р Тота Венкова - Габрово. От 2016 г. до 2017 г. практикува в ДКЦ Авис Медика Плевен и Отделение по Неонатология към МБАЛ Авис Медика. От 2017 г. работи в Отделение по неонатология УМБАЛ Георги Странски ЕАД - Гр. Плевен. Към момента има и самостоятелни практики като педиатър в Плевен и Габрово.' ,'e02fe4eb-a24b-4588-a48c-197bd00138a7');

INSERT INTO roles (id, name, deleted, date_created)
VALUES ('83f00c4a-3e7a-4f74-b8d7-04705dad0be1', 'ROLE_DOCTOR', false, (now() at time zone 'utc')),
       ('e02fe4eb-a24b-4588-a48c-197bd00138a2', 'ROLE_PATIENT', false, (now() at time zone 'utc')),
       ('e02fe4eb-a24b-4588-a48c-197bd00138a3', 'ROLE_PARENT', false, (now() at time zone 'utc')),
       ('107d12ee-6469-42ae-9439-db1d377b61a5', 'ROLE_ADMINISTRATOR', false, (now() at time zone 'utc'));

INSERT INTO application_users_roles(application_user_id, roles_id)
VALUES ('e02fe4eb-a24b-4588-a48c-197bd00138a7', '83f00c4a-3e7a-4f74-b8d7-04705dad0be1');

INSERT INTO specializations (id, name, description, doctor_id)
VALUES (1, 'Неонатология', 'Подспециалност на детските болести, която се занимава с медицински грижи за новородените, по-специално болните или недоносените.', '83f00c4a-3e7a-4f74-b8d7-04705dad0ba2'),
       (2, 'Детски болести', 'Дял от медицината, който се занимава с проследяване на физическото и нервно-психическото развитие на детския организъм, диагностика и лечения на детски заболявания.', '83f00c4a-3e7a-4f74-b8d7-04705dad0ba2');

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

INSERT INTO cabinets(id, name, city, address, post_code, phone_number, time_zone, doctor_id, deleted, date_created, work_days)
VALUES (1, 'Габрово', 'гр. Габрово', 'Медицински център Хипократ, площад Белорусия 2', '5300', '0878787342', 'Europe/Sofia', '83f00c4a-3e7a-4f74-b8d7-04705dad0ba2', false, (now() at time zone 'utc'), 'Вторник, Сряда'),
       (2, 'Плевен', 'гр. Плевен', 'ул. Стефан Караджа 49', '5800', '0878787342', 'Europe/Sofia', '83f00c4a-3e7a-4f74-b8d7-04705dad0ba2', false, (now() at time zone 'utc'), 'Понеделник, Четвъртък, Петък');

INSERT INTO schedules(id, deleted, date_created, cabinet_id)
VALUES ('dd7e7a79-18fe-46f3-9ffe-29aef8c18ed6', false, (now() at time zone 'utc'), 1),
        ('6bba2627-3f52-43f4-9c28-5ab3d7ce6151', false, (now() at time zone 'utc'), 2);

INSERT INTO appointment_causes(id, name)
VALUES (1, 'Първичен преглед'),
       (2, 'Вторичен преглед'),
       (3, 'Детско здравеопазване'),
       (4, 'Свободен прием');

INSERT INTO gallery_images(id, url, name, extension, deleted, date_created)
VALUES ('bda52fb6-90bc-48cc-b26d-7bc2599546eb', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085027/pediamed/IMG-3d4d5a079b4f712fd53ce9ad097faf24-V.jpg.jpg', 'IMG-3d4d5a079b4f712fd53ce9ad097faf24-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('2c40b051-80b9-43a5-bc4e-47a6b9ee4a0e', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085028/pediamed/IMG-9add7b0ba788ced6c9f6186f01dce2ba-V.jpg.jpg', 'IMG-9add7b0ba788ced6c9f6186f01dce2ba-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('64f4b097-788d-4de0-b424-0a3e84e8c276', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085029/pediamed/IMG-73d4aaf2edbfbd86e3bc8b6b44c1dfa2-V.jpg.jpg', 'IMG-73d4aaf2edbfbd86e3bc8b6b44c1dfa2-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('dc5cd2d2-8f34-4649-80dd-608af8ca154a', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085029/pediamed/IMG-4817c65b81006eea5c2815837337f8ed-V.jpg.jpg', 'IMG-4817c65b81006eea5c2815837337f8ed-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('81612113-7cc2-49ae-8c15-9034536e42bf', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085030/pediamed/IMG-ab0a5b033ee8cf53def9e937f6d2baf6-V.jpg.jpg', 'IMG-ab0a5b033ee8cf53def9e937f6d2baf6-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('d1519a80-7e67-4406-900c-f179859ee655', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085030/pediamed/IMG-b93856f2c12b1dcaa36a80c8b886cd30-V.jpg.jpg', 'IMG-b93856f2c12b1dcaa36a80c8b886cd30-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('93b9c6be-44a1-4a20-8772-cb3a155f8994', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085031/pediamed/IMG-e415d77485530c8a65dd356e3fe820e9-V.jpg.jpg', 'IMG-e415d77485530c8a65dd356e3fe820e9-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('cd4c73b9-b4ea-4370-8f5f-648d3fcde3bd', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085031/pediamed/IMG-f1c897a19a277fb5f6b3adc1c1668e8e-V.jpg.jpg', 'IMG-f1c897a19a277fb5f6b3adc1c1668e8e-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('37c49701-72a7-4a56-8b5e-70192d53ca69', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1640085032/pediamed/IMG-fd628ff6ab68f73643fd7b0cf41868ab-V.jpg.jpg', 'IMG-fd628ff6ab68f73643fd7b0cf41868ab-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('52478e45-964a-4cd0-8018-28a3c8c41210', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1652256086/pediamed/IMG-7900005160bfe696291341f6a16c8bcf-V.jpg.jpg', 'IMG-7900005160bfe696291341f6a16c8bcf-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('6a5d5ca0-800c-4276-b7ff-b40fc133d811', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1652256087/pediamed/IMG-6f4e7e26c8e861664444ec1e77fbc4dd-V.jpg.jpg', 'IMG-6f4e7e26c8e861664444ec1e77fbc4dd-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('ef33cf02-1fcf-46f9-aae6-8b7bb63d1c44', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1652256119/pediamed/IMG-f0a1ad6ed98f243e7a18f7f9a3d82e03-V.jpg.jpg', 'IMG-f0a1ad6ed98f243e7a18f7f9a3d82e03-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('ad1af91c-4994-4673-af8e-4816c987456e', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1652256120/pediamed/IMG-dae969e9dfe108ed8e3a36c8c9fab0b8-V.jpg.jpg', 'IMG-dae969e9dfe108ed8e3a36c8c9fab0b8-V.jpg', 'jpg', false, (now() at time zone 'utc')),
       ('92e65b9a-985f-4260-9f3e-4238a2f9ec21', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1652561163/pediamed/received_1521085174955640.jpeg.jpg', 'received_1521085174955640.jpeg', 'jpg', false, (now() at time zone 'utc')),
       ('553ad13f-7749-4ba4-be21-608d385762cf', 'https://res.cloudinary.com/dpo3vbxnl/image/upload/v1652561215/pediamed/received_1072524383362935.jpeg.jpg', 'received_1072524383362935.jpeg', 'jpg', false, (now() at time zone 'utc'));

INSERT INTO offered_service (name, price, deleted, date_created)
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
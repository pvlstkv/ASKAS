insert
into themes
(attempt_number_in_test, created_at, decryption, name, question_quantity_in_test, subject_id, updated_at)
values (3, now(), 'Про Мили и Мура и прочее', 'Конечные автоматы', 5, (select id from subjects where name like 'АЛМ'), now());

insert
into themes
(attempt_number_in_test, created_at, decryption, name, question_quantity_in_test, subject_id, updated_at)
values (3, now(), 'Про нолики и единички и прочее', 'Логические функции', 5, (select id from subjects where name like 'АЛМ'), now());

insert
into themes
(attempt_number_in_test, created_at, decryption, name, question_quantity_in_test, subject_id, updated_at)
values (3, now(), 'Про минимизацию логических функий', 'Минимизация логические функции', 5, (select id from subjects where name like 'АЛМ'), now());

insert
into themes
(attempt_number_in_test, created_at, decryption, name, question_quantity_in_test, subject_id, updated_at)
values (3, now(), 'Про процессоры и его составные части', 'Процессоры', 5, (select id from subjects where name like 'Архитектура процессоров'), now());


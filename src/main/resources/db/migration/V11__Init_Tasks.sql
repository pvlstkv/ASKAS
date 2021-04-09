/* задания по АЛМ*/
insert into tasks (description, title, type, user_id)
values ('Сдать 1 лабораторную работу', '1 лаб.раоота по логическим выражениям', 0,
        (select id from users where login like 'teacher2'));

insert into tasks (description, title, type, user_id)
values ('Сдать 2 лабораторную работу', '2 лаб.раоота по логическим таблицам', 0,
        (select id from users where login like 'teacher2'));

insert into tasks (description, title, type, user_id)
values ('Сдать 3 лабораторную работу', '3 лаб.раоота по автоматам Мили', 0,
        (select id from users where login like 'teacher2'));

/* задания по английскому*/
insert into tasks (description, title, type, user_id)
values ('Написать рассказ о себе на английском языке 10 предложений', 'О себе', 1,
        (select id from users where login like 'teacher4'));

insert into tasks (description, title, type, user_id)
values ('Написать рассказ о Лондоне на английском языке 10 предложений', 'О Лондоне', 1,
        (select id from users where login like 'teacher4'));

/* задания по немецкому*/
insert into tasks (description, title, type, user_id)
values ('Написать рассказ о себе на немецком языке 10 предложений', 'О себе', 1,
        (select id from users where login like 'teacher3'));

insert into tasks (description, title, type, user_id)
values ('Написать рассказ о Берлине на немецком языке 10 предложений', 'О Берлине', 1,
        (select id from users where login like 'teacher3'));

/* задания по Архитектуре процессоров*/
insert into tasks (description, title, type, user_id)
values ('Написать Hello world на Ассемблере', 'Привет мир', 0,
        (select id from users where login like 'teacher1'));

insert into tasks (description, title, type, user_id)
values ('Написать Калькулятор на Ассемблере', 'Калькулятор', 0,
        (select id from users where login like 'teacher1'));

insert into tasks (description, title, type, user_id)
values ('Реализовать поиск в массива', 'Массив и поиск в нем', 0,
        (select id from users where login like 'teacher1'));

/* задания по электротехнике*/
insert into tasks (description, title, type, user_id)
values ('Посчитать напряжение на конденсаторе', 'Первые шаги', 0,
        (select id from users where login like 'teacher5'));

insert into tasks (description, title, type, user_id)
values ('Нарисовать схему паралелльного подключения потребителей', 'Сделать схему', 0,
        (select id from users where login like 'teacher5'));




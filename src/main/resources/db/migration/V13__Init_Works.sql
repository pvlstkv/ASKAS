/**англ**/
insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'i tried', 'good work',
        (select id from tasks where description like 'Написать рассказ о себе на английском языке 10 предложений'),
        (select id from users where login like 'user7'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 4, 'heh', 'alright',
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'),
        (select id from users where login like 'user7'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 3, 'i tried', 'not bad',
        (select id from tasks where description like 'Написать рассказ о себе на английском языке 10 предложений'),
        (select id from users where login like 'user8'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 4, 'heh', 'alright',
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'),
        (select id from users where login like 'user8'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 3, 'i tried', 'not bad',
        (select id from tasks where description like 'Написать рассказ о себе на английском языке 10 предложений'),
        (select id from users where login like 'user9'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', 'well done',
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'),
        (select id from users where login like 'user9'));


insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', 'красава',
        (select id from tasks where description like 'Написать рассказ о себе на английском языке 10 предложений'),
        (select id from users where login like 'user1'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 4, 'heh', 'alright',
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'),
        (select id from users where login like 'user1'));

-- insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
-- values (now(), 4, 'heh', 'alright',
--         (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'),
--         (select id from users where login like 'user2'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', 'alright',
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'),
        (select id from users where login like 'user3'));


/*АЛМ*/
insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', 'красава',
        (select id from tasks where description like 'Сдать 1 лабораторную работу'),
        (select id from users where login like 'user1'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 4, 'heh', 'норм',
        (select id from tasks where description like 'Сдать 2 лабораторную работу'),
        (select id from users where login like 'user2'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 4, 'heh', 'норм',
        (select id from tasks where description like 'Сдать 2 лабораторную работу'),
        (select id from users where login like 'user3'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', '+++++++',
        (select id from tasks where description like 'Сдать 3 лабораторную работу'),
        (select id from users where login like 'user2'));

/*Архитектура процессоров*/
insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', '+++++++',
        (select id from tasks where description like 'Написать Hello world на Ассемблере'),
        (select id from users where login like 'user2'));

-- insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
-- values (now(), 4, 'heh', 'нелпохо',
--         (select id from tasks where description like 'Написать Hello world на Ассемблере'),
--         (select id from users where login like 'user1'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', 'нелпохо',
        (select id from tasks where description like 'Написать Hello world на Ассемблере'),
        (select id from users where login like 'user3'));
/**электротехника**/

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 4, 'heh', 'нелпохо',
        (select id from tasks where description like 'Нарисовать схему паралелльного подключения потребителей'),
        (select id from users where login like 'user4'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 5, 'heh', 'нелпохо',
        (select id from tasks where description like 'Нарисовать схему паралелльного подключения потребителей'),
        (select id from users where login like 'user5'));

insert into works(created_at, mark, student_comment, teacher_comment, task_id, user_id)
values (now(), 4, 'heh', 'нелпохо',
        (select id from tasks where description like 'Нарисовать схему паралелльного подключения потребителей'),
        (select id from users where login like 'user6'));





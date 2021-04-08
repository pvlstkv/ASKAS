/*1 subject and teacher*/
insert into users(email,
                  first_name,
                  last_name,
                  patronymic,
                  login,
                  password,
                  phone,
                  role,
                  department_id)
values ('email_t.com',
        'Андрей',
        'Игонин',
        'Геннадьевич',
        'teacher1',
        'teacher1',
        '112',
        1,
        (SELECT id FROM departments WHERE short_name LIKE 'ВТ'));

insert into subjects(created_at,
                     decryption,
                     name,
                     department_id)
values (now(),
        'асемблер наше все',
        'Архитектура процессоров',
        (SELECT id FROM departments WHERE short_name LIKE 'ВТ'));

insert into teacher_subject(user_id, subject_id)
values ((select id from users where login like 'teacher1'),
        (select id from subjects where name like 'Архитектура процессоров'));

/*2 subject and teacher*/
insert into users(email,
                  first_name,
                  last_name,
                  patronymic,
                  login,
                  password,
                  phone,
                  role,
                  department_id)
values ('email_t2.com',
        'Анна',
        'Лылова',
        'Вячаславовна',
        'teacher2',
        'teacher2',
        '113',
        1,
        (SELECT id FROM departments WHERE short_name LIKE 'ВТ'));

insert into subjects(created_at,
                     decryption,
                     name,
                     department_id)
values (now(),
        'наше все',
        'АЛМ',
        (SELECT id FROM departments WHERE short_name LIKE 'ВТ'));

insert into teacher_subject(user_id, subject_id)
values ((select id from users where login like 'teacher2'),
        (select id from subjects where name like 'АЛМ'));

/*3 subject and teacher*/

insert into users(email,
                  first_name,
                  last_name,
                  patronymic,
                  login,
                  password,
                  phone,
                  role,
                  department_id)
values ('email_t3.com',
        'Ирина',
        'Беляева',
        'Владимировна',
        'teacher3',
        'teacher3',
        '114',
        1,
        (SELECT id FROM departments WHERE short_name LIKE 'ИНЯЗ'));

insert into subjects(created_at,
                     decryption,
                     name,
                     department_id)
values (now(),
        'Спрехин зи дойч',
        'Немецкий язык',
        (SELECT id FROM departments WHERE short_name LIKE 'ИНЯЗ'));

insert into teacher_subject(user_id, subject_id)
values ((select id from users where login like 'teacher3'),
        (select id from subjects where name like 'Немецкий язык'));

/*4 subject and teacher*/

insert into users(email,
                  first_name,
                  last_name,
                  patronymic,
                  login,
                  password,
                  phone,
                  role,
                  department_id)
values ('email_t4.com',
        'Джон',
        'Клинт',
        'Юрьивич',
        'teacher4',
        'teacher4',
        '115',
        1,
        (SELECT id FROM departments WHERE short_name LIKE 'ИНЯЗ'));

insert into subjects(created_at,
                     decryption,
                     name,
                     department_id)
values (now(),
        'Спик инглиш',
        'Англиский язык',
        (SELECT id FROM departments WHERE short_name LIKE 'ИНЯЗ'));

insert into teacher_subject(user_id, subject_id)
values ((select id from users where login like 'teacher4'),
        (select id from subjects where name like 'Англиский язык'));

/*5 subject and teacher*/

insert into users(email,
                  first_name,
                  last_name,
                  patronymic,
                  login,
                  password,
                  phone,
                  role,
                  department_id)
values ('email_t4.com',
        'Петр',
        'Иванов',
        'Юрьивич',
        'teacher5',
        'teacher5',
        '116',
        1,
        (SELECT id FROM departments WHERE short_name LIKE 'РФ'));

insert into subjects(created_at,
                     decryption,
                     name,
                     department_id)
values (now(),
        'электро дед',
        'электротехника',
        (SELECT id FROM departments WHERE short_name LIKE 'РФ'));

insert into teacher_subject(user_id, subject_id)
values ((select id from users where login like 'teacher5'),
        (select id from subjects where name like 'электротехника'));






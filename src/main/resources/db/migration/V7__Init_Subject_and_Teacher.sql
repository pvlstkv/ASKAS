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
values ('email.com',
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
values ('email.com',
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




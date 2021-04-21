/*1 subject*/
insert into subject_semesters(control_type,
                              name,
                              created_at,
                              has_course_project,
                              has_course_work,
                              study_group_id,
                              subject_id)
values (1,
        'курс по алм для 1 го курса ИВТ',
        now(),
        true,
        false,
        (select id from study_groups where short_name like 'ИВТ'),
        (select id from subjects where name like 'АЛМ'));

/*2 subject*/
insert into subject_semesters(control_type,
                              name,
                              created_at,
                              has_course_project,
                              has_course_work,
                              study_group_id,
                              subject_id)
values (1,
        'курс по Архитектуре процессоров для 1 го курса ИВТ',
        now(),
        true,
        false,
        (select id from study_groups where short_name like 'ИВТ'),
        (select id from subjects where name like 'Архитектура процессоров'));

/*3 subject*/
insert into subject_semesters(control_type,
                              name,
                              created_at,
                              has_course_project,
                              has_course_work,
                              study_group_id,
                              subject_id)
values (1,
        'курс по Немецкий язык  для 2 го курса НемЯз',
        now(),
        true,
        false,
        (select id from study_groups where short_name like 'НемЯз'),
        (select id from subjects where name like 'Немецкий язык'));

/*4 subject*/
insert into subject_semesters(control_type,
                              name,
                              created_at,
                              has_course_project,
                              has_course_work,
                              study_group_id,
                              subject_id)
values (1,
        'курс по Англиский язык  для 2 го курса НемЯз',
        now(),
        true,
        false,
        (select id from study_groups where short_name like 'НемЯз'),
        (select id from subjects where name like 'Англиский язык'));

/*5 subject*/
insert into subject_semesters(control_type,
                              name,
                              created_at,
                              has_course_project,
                              has_course_work,
                              study_group_id,
                              subject_id)
values (1,
        'курс электротехники для  3 курса РТ',
        now(),
        true,
        false,
        (select id from study_groups where short_name like 'РТ'),
        (select id from subjects where name like 'электротехника'));

/*6 subject*/
insert into subject_semesters(control_type,
                              name,
                              created_at,
                              has_course_project,
                              has_course_work,
                              study_group_id,
                              subject_id)
values (1,
        'курс по Англиский язык  для 1 го курса ИВТ',
        now(),
        true,
        false,
        (select id from study_groups where short_name like 'ИВТ'),
        (select id from subjects where name like 'Англиский язык'));
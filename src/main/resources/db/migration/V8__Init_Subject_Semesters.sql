insert into subject_semesters(control_type,
                              created_at,
                              has_course_project,
                              has_course_work,
                              number_of_semester,
                              subject_id)
values (1,
        now(),
        true,
        false,
        1,
        (select id from subjects where name like 'АЛМ'));

insert into study_groups_subject_semesters(study_group_id, subject_semester_id)
values ((select id from departments where short_name like 'ВТ'),
        lastval());

insert into subject_semesters(control_type,
                              created_at,
                              has_course_project,
                              has_course_work,
                              number_of_semester,
                              subject_id)
values (1,
        now(),
        true,
        true,
        1,
        (select id from subjects where name like 'Архитектура процессоров')) returning id;

insert into study_groups_subject_semesters(study_group_id, subject_semester_id)
values ((select id from departments where short_name like 'ВТ'),
        lastval());

insert into study_groups(code,
                         created_at,
                         full_name,
                         group_number,
                         number_of_semester,
                         short_name,
                         year_of_study_start,
                         department_id)
values (111,
        now(),
        'информатика и вычислительная техника',
        1,
        1,
        'ИВТ',
        2020,
        (SELECT id FROM departments WHERE short_name LIKE 'ВТ'));

insert into study_groups(code,
                         created_at,
                         full_name,
                         group_number,
                         number_of_semester,
                         short_name,
                         year_of_study_start,
                         department_id)
values (111,
        now(),
        'немецкий язык',
        1,
        3,
        'НемЯз',
        2020,
        (SELECT id FROM departments WHERE short_name LIKE 'ИНЯЗ'));

insert into study_groups(code,
                         created_at,
                         full_name,
                         group_number,
                         number_of_semester,
                         short_name,
                         year_of_study_start,
                         department_id)
values (111,
        now(),
        'радиотехника',
        1,
        5,
        'РТ',
        2020,
        (SELECT id FROM departments WHERE short_name LIKE 'РФ'));



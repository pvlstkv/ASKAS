insert into study_groups(code,
                         course_number,
                         created_at,
                         full_name,
                         group_number,
                         short_name,
                         year_of_study_start,
                         department_id)
values (111,
        1,
        now(),
        'информатика и вычислительная техника',
        1,
        'ИВТ',
        2020,
        (SELECT id FROM departments WHERE short_name LIKE 'ВТ'));


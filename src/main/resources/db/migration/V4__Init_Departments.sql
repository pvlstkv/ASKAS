insert into departments(created_at, full_name, short_name, faculty_id)
    values (now(),'кафедра вычислительная техника','ВТ',(SELECT id FROM faculties WHERE short_name LIKE 'ФИСТ'));



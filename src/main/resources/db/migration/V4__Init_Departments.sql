insert into departments(created_at, full_name, short_name, faculty_id)
values (now(), 'кафедра вычислительная техника', 'ВТ', (SELECT id FROM faculties WHERE short_name LIKE 'ФИСТ'));
insert into departments(created_at, full_name, short_name, faculty_id)
values (now(), 'кафедра иностранного языка', 'ИНЯЗ',
        (SELECT id FROM faculties WHERE short_name LIKE 'ГМ'));
insert into departments(created_at, full_name, short_name, faculty_id)
values (now(), 'кафедра  ртф', 'РФ', (SELECT id FROM faculties WHERE short_name LIKE 'РТФ'));



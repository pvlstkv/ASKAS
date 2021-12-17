/*литература 1*/
insert into literature(authors, description, title, type, user_id)
values ('Лылова А В', 'учись прогать', 'АИП', 1,   (select id from users where login like 'teacher1'));
insert into literature_semester(literature_id, semester_id)
values (lastval(),(select id from subject_semesters where name like 'курс по алм для 1 го курса ИВТ'));

/*литература 2*/
insert into literature(authors, description, title, type, user_id)
values ('Игонин А Г', 'микропроцессоры нужно знать', 'МикроПроцессоры', 0,   (select id from users where login like 'teacher2'));
insert into literature_semester(literature_id, semester_id)
values (lastval(),(select id from subject_semesters where name like 'курс по Архитектуре процессоров для 1 го курса ИВТ'));

/*литература 3*/
insert into literature(authors, description, title, type, user_id)
values ('Власенко И Г', 'люблю симбирсофт', 'основы немемцого', 0,   (select id from users where login like 'teacher3'));
insert into literature_semester(literature_id, semester_id)
values (lastval(),(select id from subject_semesters where name like 'курс по Англиский язык  для 3 го курса'));

/*литература 4*/
insert into literature(authors, description, title, type, user_id)
values ('Пузова И Н', 'ви хаист ду', 'учи англ', 1,   (select id from users where login like 'teacher5'));
insert into literature_semester(literature_id, semester_id)
values (lastval(),(select id from subject_semesters where name like 'курс по Англиский язык  для 3 го курса'));

/*литература 5*/
insert into literature(authors, description, title, type, user_id)
values ('Меньшов Пирамида', 'люблю египет', 'треугольник Меньшова', 0,   (select id from users where login like 'teacher5'));
insert into literature_semester(literature_id, semester_id)
values (lastval(),(select id from subject_semesters where name like 'курс электротехники для  3 курса РТ'));


/*АЛМ*/
insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по алм для 1 го курса ИВТ'),
        (select id from tasks where title like '1 лаб.раоота по логическим выражениям'));

insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по алм для 1 го курса ИВТ'),
        (select id from tasks where title like '2 лаб.раоота по логическим таблицам'));

insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по алм для 1 го курса ИВТ'),
        (select id from tasks where title like '3 лаб.раоота по автоматам Мили'));

/*Архитектура процессора*/
insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Архитектуре процессоров для 1 го курса ИВТ'),
        (select id from tasks where title like 'Привет мир'));

insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Архитектуре процессоров для 1 го курса ИВТ'),
        (select id from tasks where title like 'Калькулятор'));

/*Англ*/
insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 3 го курса'),
        (select id from tasks where description like 'Написать рассказ о себе на английском языке 10 предложений'));

insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 3 го курса'),
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'));

/*Нем*/
insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 3 го курса'),
        (select id from tasks where description like 'Написать рассказ о себе на немецком языке 10 предложений'));

insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 3 го курса'),
        (select id from tasks where description like 'Написать рассказ о Берлине на немецком языке 10 предложений'));

/*электротехника*/
insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс электротехники для  3 курса РТ'),
        (select id from tasks where title like 'Первые шаги'));

insert into task_semester(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс электротехники для  3 курса РТ'),
        (select id from tasks where title like 'Сделать схему'));


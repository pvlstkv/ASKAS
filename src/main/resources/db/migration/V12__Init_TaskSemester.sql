/*АЛМ*/
insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по алм для 1 го курса ИВТ'),
        (select id from tasks where title like '1 лаб.раоота по логическим выражениям'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по алм для 1 го курса ИВТ'),
        (select id from tasks where title like '2 лаб.раоота по логическим таблицам'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по алм для 1 го курса ИВТ'),
        (select id from tasks where title like '3 лаб.раоота по автоматам Мили'));

/*Архитектура процессора*/
insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Архитектуре процессоров для 1 го курса ИВТ'),
        (select id from tasks where title like 'Привет мир'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Архитектуре процессоров для 1 го курса ИВТ'),
        (select id from tasks where title like 'Калькулятор'));

/*Англ*/
insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 2 го курса НемЯз'),
        (select id from tasks where description like 'Написать рассказ о себе на английском языке 10 предложений'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 2 го курса НемЯз'),
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 1 го курса ИВТ'),
        (select id from tasks where description like 'Написать рассказ о себе на английском языке 10 предложений'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 1 го курса ИВТ'),
        (select id from tasks where description like 'Написать рассказ о Лондоне на английском языке 10 предложений'));

/*Нем*/
insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 2 го курса НемЯз'),
        (select id from tasks where description like 'Написать рассказ о себе на немецком языке 10 предложений'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс по Англиский язык  для 2 го курса НемЯз'),
        (select id from tasks where description like 'Написать рассказ о Берлине на немецком языке 10 предложений'));

/*электротехника*/
insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс электротехники для  3 курса РТ'),
        (select id from tasks where title like 'Первые шаги'));

insert into semester_task(semester_id, task_id)
values ((select id from subject_semesters where name like 'курс электротехники для  3 курса РТ'),
        (select id from tasks where title like 'Сделать схему'));


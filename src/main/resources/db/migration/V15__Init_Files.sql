insert into files(access_level, content_length, content_type, link_count, name, user_id)
values (0, 15282, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 12, 'Task', 1);

insert into files(access_level, content_length, content_type, link_count, name, user_id)
values (0, 4381641, 'application/pdf', 6, 'Literature', 1);

insert into files(access_level, content_length, content_type, link_count, name, user_id)
values (0, 784159, 'application/zip', 18, 'Work', 1);

insert into files(access_level, content_length, content_type, link_count, name, user_id)
values (0, 94333, 'image/jpeg', 12, 'Scheme', 1);

insert into files(access_level, content_length, content_type, link_count, name, user_id)
values (0, 68836, 'image/jpeg', 5, 'Avatar', 1);

insert into files(access_level, content_length, content_type, link_count, name, user_id)
values (0, 370279, 'image/png', 1, 'AvatarTeacher', 1);



insert into file_task(task_id, file_id)
values (1, 1);

insert into file_task(task_id, file_id)
values (1, 4);

insert into file_task(task_id, file_id)
values (2, 1);

insert into file_task(task_id, file_id)
values (2, 4);

insert into file_task(task_id, file_id)
values (3, 1);

insert into file_task(task_id, file_id)
values (3, 4);

insert into file_task(task_id, file_id)
values (4, 1);

insert into file_task(task_id, file_id)
values (4, 4);

insert into file_task(task_id, file_id)
values (5, 1);

insert into file_task(task_id, file_id)
values (5, 4);

insert into file_task(task_id, file_id)
values (6, 1);

insert into file_task(task_id, file_id)
values (6, 4);

insert into file_task(task_id, file_id)
values (7, 1);

insert into file_task(task_id, file_id)
values (7, 4);

insert into file_task(task_id, file_id)
values (8, 1);

insert into file_task(task_id, file_id)
values (8, 4);

insert into file_task(task_id, file_id)
values (9, 1);

insert into file_task(task_id, file_id)
values (9, 4);

insert into file_task(task_id, file_id)
values (10, 1);

insert into file_task(task_id, file_id)
values (10, 4);

insert into file_task(task_id, file_id)
values (11, 1);

insert into file_task(task_id, file_id)
values (11, 4);

insert into file_task(task_id, file_id)
values (12, 1);

insert into file_task(task_id, file_id)
values (12, 4);



insert into file_literature(literature_id, file_id)
values (1, 2);

insert into file_literature(literature_id, file_id)
values (2, 2);

insert into file_literature(literature_id, file_id)
values (3, 2);

insert into file_literature(literature_id, file_id)
values (4, 2);

insert into file_literature(literature_id, file_id)
values (5, 2);

insert into file_literature(literature_id, file_id)
values (6, 2);



insert into file_work(work_id, file_id)
values (1, 3);

insert into file_work(work_id, file_id)
values (2, 3);

insert into file_work(work_id, file_id)
values (3, 3);

insert into file_work(work_id, file_id)
values (4, 3);

insert into file_work(work_id, file_id)
values (5, 3);

insert into file_work(work_id, file_id)
values (6, 3);

insert into file_work(work_id, file_id)
values (7, 3);

insert into file_work(work_id, file_id)
values (8, 3);

insert into file_work(work_id, file_id)
values (9, 3);

insert into file_work(work_id, file_id)
values (10, 3);

insert into file_work(work_id, file_id)
values (11, 3);

insert into file_work(work_id, file_id)
values (12, 3);

insert into file_work(work_id, file_id)
values (13, 3);

insert into file_work(work_id, file_id)
values (14, 3);

insert into file_work(work_id, file_id)
values (15, 3);

insert into file_work(work_id, file_id)
values (16, 3);

insert into file_work(work_id, file_id)
values (17, 3);

insert into file_work(work_id, file_id)
values (18, 3);



update users
set avatar_id = 5
where id between 2 and 6;

update users
set avatar_id = 6
where id = 11;
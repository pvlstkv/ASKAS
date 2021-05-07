create table "answer_choice"
(
    "id"          serial not null,
    "answer"      varchar(255),
    "is_right"    boolean,
    "question_id" int8,
    primary key ("id")
);
create table "conference"
(
    "id"      bigserial not null,
    "token"   varchar(255),
    "user_id" int4,
    primary key ("id")
);
create table "question"
(
    "id"            bigserial not null,
    "complexity"    float8,
    "question"      varchar(255),
    "question_type" int4,
    "subject_id"    int8,
    "theme_id"      int8,
    primary key ("id")
);
create table "question_user_files"
(
    "question_id"   int8 not null,
    "user_files_id" int8 not null,
    primary key ("question_id", "user_files_id")
);
create table "conference_group"
(
    "conference_id" int8 not null,
    "group_id"      int8 not null,
    primary key ("conference_id", "group_id")
);
create table "departments"
(
    "id"         bigserial not null,
    "created_at" timestamp,
    "full_name"  varchar(255),
    "short_name" varchar(255),
    "updated_at" timestamp,
    "faculty_id" int8,
    primary key ("id")
);
create table "faculties"
(
    "id"         bigserial not null,
    "created_at" timestamp,
    "full_name"  varchar(255),
    "short_name" varchar(255),
    "updated_at" timestamp,
    primary key ("id")
);
create table "file_literature"
(
    "literature_id" int8 not null,
    "file_id"       int8 not null,
    primary key ("literature_id", "file_id")
);
create table "file_task"
(
    "task_id" int8 not null,
    "file_id" int8 not null,
    primary key ("task_id", "file_id")
);
create table "file_work"
(
    "work_id" int8 not null,
    "file_id" int8 not null,
    primary key ("work_id", "file_id")
);
create table "files"
(
    "id"             bigserial not null,
    "access_level"   int4,
    "content_length" int8,
    "content_type"   varchar(255),
    "link_count"     int4,
    "name"           varchar(50),
    "user_id"        int4,
    primary key ("id")
);
create table "literature"
(
    "id"          bigserial not null,
    "authors"     varchar(50),
    "description" varchar(1000000),
    "title"       varchar(50),
    "type"        int4,
    "user_id"     int4,
    primary key ("id")
);
create table "literature_semester"
(
    "literature_id" int8 not null,
    "semester_id"   int8 not null,
    primary key ("literature_id", "semester_id")
);
create table "passed_questions"
(
    "id"             bigserial not null,
    "passed_test_id" int8,
    "question_id"    int8,
    primary key ("id")
);
create table "passed_tests"
(
    "id"                bigserial not null,
    "passed_at"         timestamp,
    "rating_in_percent" int4,
    "theme_id"          int8,
    "user_id"           int4,
    primary key ("id")
);
create table "schedule"
(
    "id"           bigserial not null,
    "info"         varchar(255),
    "name_group"   varchar(255),
    "number_day"   int4,
    "number_week"  int4,
    "pair_number"  int4,
    "place"        varchar(255),
    "subgroup"     int4,
    "subject"      varchar(255),
    "teacher"      varchar(255),
    "type_subject" int4,
    primary key ("id")
);
create table "semester_marks"
(
    "id"                  bigserial not null,
    "mark"                int4,
    "subject_semester_id" int8,
    "user_id"             int4,
    primary key ("id")
);
create table "study_groups"
(
    "id"                  bigserial not null,
    "code"                int4,
    "created_at"          timestamp,
    "full_name"           varchar(50),
    "group_number"        int4,
    "number_of_semester"  int4,
    "short_name"          varchar(50),
    "updated_at"          timestamp,
    "year_of_study_start" int4,
    "department_id"       int8,
    primary key ("id")
);
create table "subject_semesters"
(
    "id"                 bigserial not null,
    "control_type"       int4,
    "created_at"         timestamp,
    "has_course_project" boolean,
    "has_course_work"    boolean,
    "name"               varchar(255),
    "updated_at"         timestamp,
    "study_group_id"     int8,
    "subject_id"         int8,
    primary key ("id")
);
create table "subjects"
(
    "id"            bigserial not null,
    "created_at"    timestamp,
    "decryption"    varchar(1000000),
    "name"          varchar(50),
    "updated_at"    timestamp,
    "department_id" int8,
    primary key ("id")
);
create table "task_semester"
(
    "task_id"     int8 not null,
    "semester_id" int8 not null,
    primary key ("task_id", "semester_id")
);
create table "tasks"
(
    "id"          bigserial not null,
    "description" varchar(1000000),
    "title"       varchar(50),
    "type"        int4,
    "user_id"     int4,
    primary key ("id")
);
create table "teacher_subject"
(
    "user_id"    int4 not null,
    "subject_id" int8 not null,
    primary key ("subject_id", "user_id")
);
create table "themes"
(
    "id"                        bigserial not null,
    "attempt_number_in_test"    int4,
    "created_at"                timestamp,
    "decryption"                varchar(255),
    "name"                      varchar(255),
    "question_quantity_in_test" int4,
    "updated_at"                timestamp,
    "subject_id"                int8,
    primary key ("id")
);
create table "user_answers"
(
    "id"                 bigserial not null,
    "answer"             varchar(255),
    "is_right"           boolean,
    "passed_question_id" int8,
    primary key ("id")
);
create table "users"
(
    "id"             serial not null,
    "email"          varchar(255),
    "first_name"     varchar(255),
    "last_name"      varchar(255),
    "login"          varchar(255),
    "password"       varchar(255),
    "patronymic"     varchar(255),
    "phone"          varchar(255),
    "role"           int4,
    "department_id"  int8,
    "study_group_id" int8,
    primary key ("id")
);
create table "works"
(
    "id"              bigserial not null,
    "created_at"      timestamp,
    "mark"            int4,
    "student_comment" varchar(1000000),
    "teacher_comment" varchar(1000000),
    "updated_at"      timestamp,
    "task_id"         int8,
    "user_id"         int4,
    primary key ("id")
);
alter table "question_user_files"
    add constraint user_files_id unique ("user_files_id");
alter table "answer_choice"
    add constraint "question_id" foreign key ("question_id") references "question";
alter table "conference"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "question"
    add constraint "subject_id" foreign key ("subject_id") references "subjects";
alter table "question"
    add constraint "theme_id" foreign key ("theme_id") references "themes";
alter table "question_user_files"
    add constraint "user_files_idd" foreign key ("user_files_id") references "files";
alter table "question_user_files"
    add constraint "question_id" foreign key ("question_id") references "question";
alter table "conference_group"
    add constraint "group_id" foreign key ("group_id") references "study_groups";
alter table "conference_group"
    add constraint "conference_id" foreign key ("conference_id") references "conference";
alter table "departments"
    add constraint "faculty_id" foreign key ("faculty_id") references "faculties";
alter table "file_literature"
    add constraint "file_id" foreign key ("file_id") references "files";
alter table "file_literature"
    add constraint "literature_id" foreign key ("literature_id") references "literature";
alter table "file_task"
    add constraint "file_id" foreign key ("file_id") references "files";
alter table "file_task"
    add constraint "task_id" foreign key ("task_id") references "tasks";
alter table "file_work"
    add constraint "file_id" foreign key ("file_id") references "files";
alter table "file_work"
    add constraint "work_id" foreign key ("work_id") references "works";
alter table "files"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "literature"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "literature_semester"
    add constraint "semester_id" foreign key ("semester_id") references "subject_semesters";
alter table "literature_semester"
    add constraint "literature_id" foreign key ("literature_id") references "literature";
alter table "passed_questions"
    add constraint "passed_test_id" foreign key ("passed_test_id") references "passed_tests";
alter table "passed_questions"
    add constraint "question_id" foreign key ("question_id") references "question";
alter table "passed_tests"
    add constraint "theme_id" foreign key ("theme_id") references "themes";
alter table "passed_tests"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "semester_marks"
    add constraint "subject_semester_id" foreign key ("subject_semester_id") references "subject_semesters";
alter table "semester_marks"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "study_groups"
    add constraint "department_id" foreign key ("department_id") references "departments";
alter table "subject_semesters"
    add constraint "study_group_id" foreign key ("study_group_id") references "study_groups";
alter table "subject_semesters"
    add constraint "subject_id" foreign key ("subject_id") references "subjects";
alter table "subjects"
    add constraint "department_id" foreign key ("department_id") references "departments";
alter table "task_semester"
    add constraint "semester_id" foreign key ("semester_id") references "subject_semesters";
alter table "task_semester"
    add constraint "task_id" foreign key ("task_id") references "tasks";
alter table "tasks"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "teacher_subject"
    add constraint "subject_id" foreign key ("subject_id") references "subjects";
alter table "teacher_subject"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "themes"
    add constraint "subject_id" foreign key ("subject_id") references "subjects";
alter table "user_answers"
    add constraint "passed_question_id" foreign key ("passed_question_id") references "passed_questions";
alter table "users"
    add constraint "department_id" foreign key ("department_id") references "departments";
alter table "users"
    add constraint "study_group_id" foreign key ("study_group_id") references "study_groups";
alter table "works"
    add constraint "task_id" foreign key ("task_id") references "tasks";
alter table "works"
    add constraint "user_id" foreign key ("user_id") references "users";
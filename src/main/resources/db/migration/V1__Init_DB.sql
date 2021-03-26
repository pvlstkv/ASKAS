
create table "answer_choice"
(
    "id"          serial not null,
    "answer"      varchar(255),
    "is_right"    boolean,
    "question_id" int8,
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
create table "departments"
(
    "id"         bigserial not null,
    "created_at" timestamp,
    "full_name"  varchar(255) NOT NULL UNIQUE,
    "short_name" varchar(255) NOT NULL UNIQUE,
    "updated_at" timestamp,
    "faculty_id" int8,
    primary key ("id")
);
create table "faculties"
(
    "id"         bigserial not null,
    "created_at" timestamp,
    "full_name"  varchar(255) NOT NULL UNIQUE,
    "short_name" varchar(255) NOT NULL UNIQUE,
    "updated_at" timestamp,
    primary key ("id")
);
create table "files"
(
    "id"              bigserial not null,
    "access_level"    int4,
    "data"            bytea,
    "description"     varchar(255),
    "name"            varchar(255),
    "study_file_type" int4,
    "question_id"     int8,
    "task_id"         int8,
    "user_id"         int4,
    "work_id"         int8,
    primary key ("id")
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
    "course_number"       int4,
    "created_at"          timestamp,
    "full_name"           varchar(255) NOT NULL UNIQUE,
    "group_number"        int4,
    "short_name"          varchar(255) NOT NULL UNIQUE,
    "updated_at"          timestamp,
    "year_of_study_start" int4,
    "department_id"       int8,
    primary key ("id")
);
create table "study_groups_subject_semesters"
(
    "study_group_id"      int8 not null,
    "subject_semester_id" int8 not null,
    primary key ("study_group_id", "subject_semester_id")
);
create table "subject_semesters"
(
    "id"                 bigserial not null,
    "control_type"       int4,
    "created_at"         timestamp,
    "has_course_project" boolean,
    "has_course_work"    boolean,
    "number_of_semester" int4,
    "updated_at"         timestamp,
    "subject_id"         int8,
    primary key ("id")
);
create table "subjects"
(
    "id"            bigserial not null,
    "created_at"    timestamp,
    "decryption"    varchar(255),
    "name"          varchar(255) NOT NULL UNIQUE,
    "updated_at"    timestamp,
    "department_id" int8,
    primary key ("id")
);
create table "tasks"
(
    "id"          bigserial not null,
    "description" varchar(255),
    "title"       varchar(255),
    "type"        int4,
    "semester_id" int8,
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
    "id"         bigserial not null,
    "created_at" timestamp,
    "decryption" varchar(255),
    "name"       varchar(255),
    "updated_at" timestamp,
    "subject_id" int8,
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
    "student_comment" varchar(255),
    "teacher_comment" varchar(255),
    "updated_at"      timestamp,
    "task_id"         int8,
    "user_id"         int4,
    primary key ("id")
);
alter table "answer_choice"
    add constraint "question_id" foreign key ("question_id") references "question";
alter table "question"
    add constraint "subject_id" foreign key ("subject_id") references "subjects";
alter table "question"
    add constraint "theme_id" foreign key ("theme_id") references "themes";
alter table "departments"
    add constraint "faculty_id" foreign key ("faculty_id") references "faculties";
alter table "files"
    add constraint "question_id" foreign key ("question_id") references "question";
alter table "files"
    add constraint "task_id" foreign key ("task_id") references "tasks";
alter table "files"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "files"
    add constraint "work_id" foreign key ("work_id") references "works";
alter table "passed_questions"
    add constraint "passed_test_id" foreign key ("passed_test_id") references "passed_tests";
alter table "passed_questions"
    add constraint "question_id" foreign key ("question_id") references "question";
alter table "passed_tests"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "semester_marks"
    add constraint "subject_semester_id" foreign key ("subject_semester_id") references "subject_semesters";
alter table "semester_marks"
    add constraint "user_id" foreign key ("user_id") references "users";
alter table "study_groups"
    add constraint "department_id" foreign key ("department_id") references "departments";
alter table "study_groups_subject_semesters"
    add constraint "subject_semester_id" foreign key ("subject_semester_id") references "subject_semesters";
alter table "study_groups_subject_semesters"
    add constraint "study_group_id" foreign key ("study_group_id") references "study_groups";
alter table "subject_semesters"
    add constraint "subject_id" foreign key ("subject_id") references "subjects";
alter table "subjects"
    add constraint "department_id" foreign key ("department_id") references "departments";
alter table "tasks"
    add constraint "semester_id" foreign key ("semester_id") references "subject_semesters";
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
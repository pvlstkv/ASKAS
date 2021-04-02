create table "answer_choice" (
                                 "id"  serial not null,
                                 "answer" varchar(255),
                                 "is_right" boolean,
                                 "question_id" int8,
                                 primary key ("id")
);


create table "departments" (
                               "id"  bigserial not null,
                               "created_at" timestamp,
                               "full_name" varchar(255),
                               "short_name" varchar(255),
                               "updated_at" timestamp,
                               "faculty_id" int8,
                               primary key ("id")
);


create table "faculties" (
                             "id"  bigserial not null,
                             "created_at" timestamp,
                             "full_name" varchar(255),
                             "short_name" varchar(255),
                             "updated_at" timestamp,
                             primary key ("id")
);


create table "files" (
                         "id"  bigserial not null,
                         "access_level" int4,
                         "data" bytea,
                         "name" varchar(255),
                         "question_id" int8,
                         "task_id" int8,
                         "user_id" int4,
                         "work_id" int8,
                         primary key ("id")
);


create table "passed_questions" (
                                    "id"  bigserial not null,
                                    "passed_test_id" int8,
                                    "question_id" int8,
                                    primary key ("id")
);


create table "passed_tests" (
                                "id"  bigserial not null,
                                "passed_at" timestamp,
                                "rating_in_percent" int4,
                                "theme_id" int8,
                                "user_id" int4,
                                primary key ("id")
);


create table "question" (
                             "id"  bigserial not null,
                             "complexity" float8,
                             "question" varchar(255),
                             "question_type" int4,
                             "subject_id" int8,
                             "theme_id" int8,
                             primary key ("id")
);


create table "schedule" (
                            "id"  bigserial not null,
                            "info" varchar(255),
                            "name_group" varchar(255),
                            "number_day" int4,
                            "number_week" int4,
                            "pair_number" int4,
                            "place" varchar(255),
                            "subgroup" int4,
                            "subject" varchar(255),
                            "teacher" varchar(255),
                            "type_subject" int4,
                            primary key ("id")
);


create table "semester_marks" (
                                  "id"  bigserial not null,
                                  "mark" int4,
                                  "subject_semester_id" int8,
                                  "user_id" int4,
                                  primary key ("id")
);


create table "study_groups" (
                                "id"  bigserial not null,
                                "code" int4,
                                "created_at" timestamp,
                                "full_name" varchar(255),
                                "group_number" int4,
                                "number_of_semester" int4,
                                "short_name" varchar(255),
                                "updated_at" timestamp,
                                "year_of_study_start" int4,
                                "department_id" int8,
                                primary key ("id")
);


create table "study_groups_subject_semesters" (
                                                  "study_group_id" int8 not null,
                                                  "subject_semester_id" int8 not null,
                                                  primary key ("study_group_id", "subject_semester_id")
);


create table "subject_semesters" (
                                     "id"  bigserial not null,
                                     "control_type" int4,
                                     "created_at" timestamp,
                                     "has_course_project" boolean,
                                     "has_course_work" boolean,
                                     "updated_at" timestamp,
                                     "subject_id" int8,
                                     primary key ("id")
);


create table "subjects" (
                            "id"  bigserial not null,
                            "created_at" timestamp,
                            "decryption" varchar(255),
                            "name" varchar(255),
                            "updated_at" timestamp,
                            "department_id" int8,
                            primary key ("id")
);


create table "tasks" (
                         "id"  bigserial not null,
                         "description" varchar(255),
                         "title" varchar(255),
                         "type" int4,
                         "semester_id" int8,
                         "user_id" int4,
                         primary key ("id")
);


create table "teacher_subject" (
                                   "user_id" int4 not null,
                                   "subject_id" int8 not null,
                                   primary key ("subject_id", "user_id")
);


create table "themes" (
                          "id"  bigserial not null,
                          "attempt_number_in_test" int4,
                          "created_at" timestamp,
                          "decryption" varchar(255),
                          "name" varchar(255),
                          "question_quantity_in_test" int4,
                          "updated_at" timestamp,
                          "subject_id" int8,
                          primary key ("id")
);


create table "user_answers" (
                                "id"  bigserial not null,
                                "answer" varchar(255),
                                "is_right" boolean,
                                "passed_question_id" int8,
                                primary key ("id")
);


create table "users" (
                         "id"  serial not null,
                         "email" varchar(255),
                         "first_name" varchar(255),
                         "last_name" varchar(255),
                         "login" varchar(255),
                         "password" varchar(255),
                         "patronymic" varchar(255),
                         "phone" varchar(255),
                         "role" int4,
                         "department_id" int8,
                         "study_group_id" int8,
                         primary key ("id")
);


create table "works" (
                         "id"  bigserial not null,
                         "created_at" timestamp,
                         "mark" int4,
                         "student_comment" varchar(255),
                         "teacher_comment" varchar(255),
                         "updated_at" timestamp,
                         "task_id" int8,
                         "user_id" int4,
                         primary key ("id")
);


alter table "answer_choice"
    add constraint "FKqoixerm93tv1pccw9wrmv81m2"
        foreign key ("question_id")
            references "question";


alter table "departments"
    add constraint "FK3s8plsoc0lynf5j8oi7b5q3u1"
        foreign key ("faculty_id")
            references "faculties";


alter table "files"
    add constraint "FKpwr6sw21c5sdxvejpbqlmudeo"
        foreign key ("question_id")
            references "question";


alter table "files"
    add constraint "FKbon9qrhh9df3dps5j5fi1a4tc"
        foreign key ("task_id")
            references "tasks";


alter table "files"
    add constraint "FK70hxcygu4axwbhpdfap1scoxe"
        foreign key ("user_id")
            references "users";


alter table "files"
    add constraint "FKdkbdy9q2jonhf8w1136517aqx"
        foreign key ("work_id")
            references "works";


alter table "passed_questions"
    add constraint "FKqxpxdaungecgp1npdix6chvb3"
        foreign key ("passed_test_id")
            references "passed_tests";


alter table "passed_questions"
    add constraint "FKptjj7pmpuk3pxpk3b7f0hxvur"
        foreign key ("question_id")
            references "question";


alter table "passed_tests"
    add constraint "FK8dphk16x4382ahrl2mepha0p9"
        foreign key ("theme_id")
            references "themes";


alter table "passed_tests"
    add constraint "FKdgr5nljms5odqiikj03994ykv"
        foreign key ("user_id")
            references "users";


alter table "question"
    add constraint "FKhhlhkw6uq11q1eegxe3up4uyl"
        foreign key ("subject_id")
            references "subjects";


alter table "question"
    add constraint "FKkwfijsvtct0h1mvwbxo40eail"
        foreign key ("theme_id")
            references "themes";


alter table "semester_marks"
    add constraint "FK81umjvayuu43t56amn254uxp8"
        foreign key ("subject_semester_id")
            references "subject_semesters";


alter table "semester_marks"
    add constraint "FK921spfn5gxq0ar7vqxyrojaoy"
        foreign key ("user_id")
            references "users";


alter table "study_groups"
    add constraint "FKa4185p8ec0p20m9ueib2ddbe9"
        foreign key ("department_id")
            references "departments";


alter table "study_groups_subject_semesters"
    add constraint "FKsp95lhlcxh7tdp8nu32sl3pv0"
        foreign key ("subject_semester_id")
            references "subject_semesters";


alter table "study_groups_subject_semesters"
    add constraint "FKscm4x7x3jw5av7xuxmp6kh1ok"
        foreign key ("study_group_id")
            references "study_groups";


alter table "subject_semesters"
    add constraint "FKo3gq9snplsuyy8fw3kh0jetli"
        foreign key ("subject_id")
            references "subjects";


alter table "subjects"
    add constraint "FK87octokwu9ksi3l68kcrdwy8q"
        foreign key ("department_id")
            references "departments";


alter table "tasks"
    add constraint "FKpp2o3fv8ydkpficsrq3sh44en"
        foreign key ("semester_id")
            references "subject_semesters";


alter table "tasks"
    add constraint "FKerjv7s3gg14l9r6fug99597vq"
        foreign key ("user_id")
            references "users";


alter table "teacher_subject"
    add constraint "FKgsge0u48er7ky9qg0q74jkfgx"
        foreign key ("subject_id")
            references "subjects";


alter table "teacher_subject"
    add constraint "FKnhlcm26sl25yqdk3ckpycq2m4"
        foreign key ("user_id")
            references "users";


alter table "themes"
    add constraint "FKk9y3oaalhtb2wx2482rmc3gp9"
        foreign key ("subject_id")
            references "subjects";


alter table "user_answers"
    add constraint "FK3elt386mn0ah1cnra6fr8k76p"
        foreign key ("passed_question_id")
            references "passed_questions";


alter table "users"
    add constraint "FKowjuwfuaig2ujr9bqqvcl336t"
        foreign key ("department_id")
            references "departments";


alter table "users"
    add constraint "FKmvx1us0w8j1d3w5252vnjd1u"
        foreign key ("study_group_id")
            references "study_groups";


alter table "works"
    add constraint "FKf0vrd9bu8xax6y5g4w70gg405"
        foreign key ("task_id")
            references "tasks";


alter table "works"
    add constraint "FKmj239ekx8jm4krh6yp9pm5mqc"
        foreign key ("user_id")
            references "users";
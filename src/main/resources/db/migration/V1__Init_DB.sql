
create table "answer_choice"
(
    "id"          serial not null,
    "answer"      varchar(255),
    "is_right"    boolean,
    "question_id" int8,
    primary key ("id")
);
create table "answer_option"
(
    "id"                     bigserial not null,
    "answer"                 varchar(255),
    "is_right"               boolean,
    "file_id"                int8,
    "selectable_question_id" int8,
    primary key ("id")
);
create table "conference"
(
    "id"      bigserial not null,
    "token"   varchar(255),
    "user_id" int4,
    primary key ("id")
);
create table "matchable_answer_option"
(
    "id"                    bigserial not null,
    "key_id"                int8,
    "matchable_question_id" int8,
    "value_id"              int8,
    primary key ("id")
);
create table "passed_matchable_answer"
(
    "id"                           bigserial not null,
    "is_right"                     boolean,
    "key_id"                       int8,
    "passed_matchable_question_id" int8,
    "value_id"                     int8,
    primary key ("id")
);
create table "passed_question_data"
(
    "passed_question_discriminator" varchar(31) not null,
    "id"                            bigserial   not null,
    "passed_test_id"                int8,
    "question_data_id"              int8,
    primary key ("id")
);
create table "passed_selectable_answer"
(
    "id"                            bigserial not null,
    "is_right"                      boolean,
    "passed_selectable_question_id" int8,
    "user_answer_id"                int8,
    primary key ("id")
);
create table "passed_testn"
(
    "id"                bigserial not null,
    "passed_at"         timestamp,
    "rating_in_percent" int4,
    "theme_id"          int8,
    "user_id"           int4,
    primary key ("id")
);
create table "passed_writeable_answer"
(
    "id"                           bigserial not null,
    "is_right"                     boolean,
    "user_answer"                  varchar(255),
    "passed_writeable_question_id" int8,
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
create table "question_data"
(
    "question_discriminator" varchar(31) not null,
    "id"                     bigserial   not null,
    "complexity"             float8,
    "question"               varchar(255),
    "question_type"          int4,
    "subject_id"             int8,
    "theme_id"               int8,
    primary key ("id")
);
create table "question_data_user_files"
(
    "question_data_id" int8 not null,
    "user_files_id"    int8 not null,
    primary key ("question_data_id", "user_files_id")
);
create table "question_user_files"
(
    "question_id"   int8 not null,
    "user_files_id" int8 not null,
    primary key ("question_id", "user_files_id")
);
create table "writeable_answer_option"
(
    "id"                    bigserial not null,
    "answer"                varchar(255),
    "is_strict"             boolean,
    "writeable_question_id" int8,
    primary key ("id")
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
create table "journals"
(
    "id"                  bigserial not null,
    "created_by"          int4,
    "created_date"        timestamp,
    "last_modified_by"    int4,
    "last_modified_date"  timestamp,
    "comment"             varchar(255),
    "lesson_date"         timestamp,
    "study_group_id"      int8,
    "subject_semester_id" int8,
    "teacher_id"          int4,
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
create table "user_contacts"
(
    "id"      bigserial not null,
    "type"    varchar(255),
    "value"   varchar(255),
    "user_id" int4,
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
    "avatar_id"      int8,
    "department_id"  int8,
    "study_group_id" int8,
    primary key ("id")
);
create table "visits"
(
    "id"                 bigserial not null,
    "created_by"         int4,
    "created_date"       timestamp,
    "last_modified_by"   int4,
    "last_modified_date" timestamp,
    "comment"            varchar(255),
    "is_visited"         boolean,
    "journal_id"         int8,
    "user_id"            int4,
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
create table "action"
(
    "id"             bigserial not null,
    "action_date"    date,
    "description"    varchar(16200),
    "title"          varchar(255),
    "action_type_id" int8      not null,
    primary key ("id")
);
create table "action_type"
(
    "id"   bigserial not null,
    "type" varchar(255),
    primary key ("id")
);

create table "users_action"
(
    "action_id" int8 not null,
    "user_id"   int4 not null,
    primary key ("action_id", "user_id")
);

alter table "question_data_user_files"
    add constraint UK_hm8yedkntukrf84ijo6vqdick unique ("user_files_id");
alter table "question_user_files"
    add constraint UK_6oh4clhdofsmggjecevjwi1xe unique ("user_files_id");
alter table "answer_choice"
    add constraint "FKato6fndoobpapfedw7tra8md2" foreign key ("question_id") references "question";
alter table "answer_option"
    add constraint "FKjkdibg9f7jteomksecbljfpwo" foreign key ("file_id") references "files";
alter table "answer_option"
    add constraint "FKcl0ewlyjlhxuh7c30dm3qck8n" foreign key ("selectable_question_id") references "question_data";
alter table "conference"
    add constraint "FK27ftffvfa3f8x9fvrbhitncn8" foreign key ("user_id") references "users";
alter table "matchable_answer_option"
    add constraint "FKkmas5mbg7mn3ggocr3v98fxp8" foreign key ("key_id") references "answer_option";
alter table "matchable_answer_option"
    add constraint "FKli9obwfca6ipc1alllac4dj3u" foreign key ("matchable_question_id") references "question_data";
alter table "matchable_answer_option"
    add constraint "FK11nnl4okl7vsuq62392ay02i7" foreign key ("value_id") references "answer_option";
alter table "passed_matchable_answer"
    add constraint "FKtq0hl1lbcm3ofsno5v6t8ehsu" foreign key ("key_id") references "answer_option";
alter table "passed_matchable_answer"
    add constraint "FKi1hsbu25bk46ks6s7g83murxb" foreign key ("passed_matchable_question_id") references "passed_question_data";
alter table "passed_matchable_answer"
    add constraint "FKkvxxyst5v8otm0ksnsrrtg56p" foreign key ("value_id") references "answer_option";
alter table "passed_question_data"
    add constraint "FKjrq1jvptlv18jwmvcegi5b26a" foreign key ("passed_test_id") references "passed_testn";
alter table "passed_question_data"
    add constraint "FKgfs6sohus6eyhv4ool4vya12h" foreign key ("question_data_id") references "question_data";
alter table "passed_selectable_answer"
    add constraint "FK5xrd3wkkgyqa7l0v5tw04ni0a" foreign key ("passed_selectable_question_id") references "passed_question_data";
alter table "passed_selectable_answer"
    add constraint "FKho5b8rpj7ocyw86gdgeq9dnqt" foreign key ("user_answer_id") references "answer_option";
alter table "passed_testn"
    add constraint "FKlckfy7mgtt7al0vm1f2xlbkm3" foreign key ("theme_id") references "themes";
alter table "passed_testn"
    add constraint "FK5baihg6undnpt7crn22m0djfy" foreign key ("user_id") references "users";
alter table "passed_writeable_answer"
    add constraint "FK2gljutru34fmcx4gl92eyasud" foreign key ("passed_writeable_question_id") references "passed_question_data";
alter table "question"
    add constraint "FKpghg1kiitomybfduse6wwx90h" foreign key ("subject_id") references "subjects";
alter table "question"
    add constraint "FKaue9153g00pml9q8dk0remvmp" foreign key ("theme_id") references "themes";
alter table "question_data"
    add constraint "FK3fjypj70dqj76x3hhb4w05vuj" foreign key ("subject_id") references "subjects";
alter table "question_data"
    add constraint "FKp64m1jeg6et82kx5a96qlqxbq" foreign key ("theme_id") references "themes";
alter table "question_data_user_files"
    add constraint "FKr3k8nug04x1uk9s8mq513y4cc" foreign key ("user_files_id") references "files";
alter table "question_data_user_files"
    add constraint "FKtgihvg23xgih2tsko7k9hufg1" foreign key ("question_data_id") references "question_data";
alter table "question_user_files"
    add constraint "FKm3p9nxy7yfk45wjx6ie2wfjtl" foreign key ("user_files_id") references "files";
alter table "question_user_files"
    add constraint "FKf74xky0knal8rf4gw3u27wytq" foreign key ("question_id") references "question";
alter table "writeable_answer_option"
    add constraint "FK61axo4lb6u8q8gn5qq7uwd6vf" foreign key ("writeable_question_id") references "question_data";
alter table "conference_group"
    add constraint "FKlu06qq185o1l7rujhy9ow6pj7" foreign key ("group_id") references "study_groups";
alter table "conference_group"
    add constraint "FKibapaprc979sjo6jgi8m6p6ey" foreign key ("conference_id") references "conference";
alter table "departments"
    add constraint "FK3s8plsoc0lynf5j8oi7b5q3u1" foreign key ("faculty_id") references "faculties";
alter table "file_literature"
    add constraint "FKhldya1kk0qfkuiwbcb7ccrlbb" foreign key ("file_id") references "files";
alter table "file_literature"
    add constraint "FKt0ml5dgjje6mv7dk3nj6txs1a" foreign key ("literature_id") references "literature";
alter table "file_task"
    add constraint "FKkrjyfxyaop481ewgttodtq6ms" foreign key ("file_id") references "files";
alter table "file_task"
    add constraint "FK2mm04whgfrrb5oaepnl7esfcx" foreign key ("task_id") references "tasks";
alter table "file_work"
    add constraint "FKfi0i3erlnngps3yws6v2fe4en" foreign key ("file_id") references "files";
alter table "file_work"
    add constraint "FKe428aymy2at6fh1id6d269dq0" foreign key ("work_id") references "works";
alter table "files"
    add constraint "FK70hxcygu4axwbhpdfap1scoxe" foreign key ("user_id") references "users";
alter table "journals"
    add constraint "FK2vf3g6qjct1fild6ldulbe9ff" foreign key ("study_group_id") references "study_groups";
alter table "journals"
    add constraint "FK5m74q624tig3804q7gh5vr50p" foreign key ("subject_semester_id") references "subject_semesters";
alter table "journals"
    add constraint "FKgp9e2fvvy6ih999pgrww5ro2e" foreign key ("teacher_id") references "users";
alter table "literature"
    add constraint "FKt8sgjiyivto8diw5ojj19jd02" foreign key ("user_id") references "users";
alter table "literature_semester"
    add constraint "FK3b5hu2crc2q5qw84jeff9f5ll" foreign key ("semester_id") references "subject_semesters";
alter table "literature_semester"
    add constraint "FKmjv9r2laxjiy9brqpgpq1offq" foreign key ("literature_id") references "literature";
alter table "passed_questions"
    add constraint "FKqxpxdaungecgp1npdix6chvb3" foreign key ("passed_test_id") references "passed_tests";
alter table "passed_questions"
    add constraint "FKd4l0tf335wf0csc1sbkwuvhh5" foreign key ("question_id") references "question";
alter table "passed_tests"
    add constraint "FK8dphk16x4382ahrl2mepha0p9" foreign key ("theme_id") references "themes";
alter table "passed_tests"
    add constraint "FKdgr5nljms5odqiikj03994ykv" foreign key ("user_id") references "users";
alter table "semester_marks"
    add constraint "FK81umjvayuu43t56amn254uxp8" foreign key ("subject_semester_id") references "subject_semesters";
alter table "semester_marks"
    add constraint "FK921spfn5gxq0ar7vqxyrojaoy" foreign key ("user_id") references "users";
alter table "study_groups"
    add constraint "FKa4185p8ec0p20m9ueib2ddbe9" foreign key ("department_id") references "departments";
alter table "subject_semesters"
    add constraint "FK7tns2hdifij2p08dt29uc8yob" foreign key ("study_group_id") references "study_groups";
alter table "subject_semesters"
    add constraint "FKo3gq9snplsuyy8fw3kh0jetli" foreign key ("subject_id") references "subjects";
alter table "subjects"
    add constraint "FK87octokwu9ksi3l68kcrdwy8q" foreign key ("department_id") references "departments";
alter table "task_semester"
    add constraint "FKqsjo8wlx0p7364p21ihbr15af" foreign key ("semester_id") references "subject_semesters";
alter table "task_semester"
    add constraint "FK3gl547chhe9sxcof4h4bive84" foreign key ("task_id") references "tasks";
alter table "tasks"
    add constraint "FKerjv7s3gg14l9r6fug99597vq" foreign key ("user_id") references "users";
alter table "teacher_subject"
    add constraint "FKgsge0u48er7ky9qg0q74jkfgx" foreign key ("subject_id") references "subjects";
alter table "teacher_subject"
    add constraint "FKnhlcm26sl25yqdk3ckpycq2m4" foreign key ("user_id") references "users";
alter table "themes"
    add constraint "FKk9y3oaalhtb2wx2482rmc3gp9" foreign key ("subject_id") references "subjects";
alter table "user_answers"
    add constraint "FK3elt386mn0ah1cnra6fr8k76p" foreign key ("passed_question_id") references "passed_questions";
alter table "user_contacts"
    add constraint "FKhsqvk5ml7ct8a311dxsjw7e6s" foreign key ("user_id") references "users";
alter table "users"
    add constraint "FKtakrrot3uqa3pk5e0jq6lh4ih" foreign key ("avatar_id") references "files";
alter table "users"
    add constraint "FKowjuwfuaig2ujr9bqqvcl336t" foreign key ("department_id") references "departments";
alter table "users"
    add constraint "FKmvx1us0w8j1d3w5252vnjd1u" foreign key ("study_group_id") references "study_groups";
alter table "visits"
    add constraint "FK8x11v1wv53xudrvvixgxq0hd5" foreign key ("journal_id") references "journals";
alter table "visits"
    add constraint "FK3d546jvep8uy0qq1q6wom2imt" foreign key ("user_id") references "users";
alter table "works"
    add constraint "FKf0vrd9bu8xax6y5g4w70gg405" foreign key ("task_id") references "tasks";
alter table "works"
    add constraint "FKmj239ekx8jm4krh6yp9pm5mqc" foreign key ("user_id") references "users";
alter table "action"
    add constraint "FKgvypp566mftb23nd5ts195p42" foreign key ("action_type_id") references "action_type";
alter table "users_action"
    add constraint "FKlf06jhmk4g7ky5rtroe2p3ayf" foreign key ("user_id") references "users";
alter table "users_action"
    add constraint "FKagse6r0u1qalmv1a27y9dw4mk" foreign key ("action_id") references "action";
/* create table "answer_choice" ("id"  serial not null, "answer" varchar(255), "is_right" boolean, "question_id" int8, primary key ("id"));
     create table "answer_option" ("id"  bigserial not null, "answer" varchar(255), "is_right" boolean, "file_id" int8, "selectable_question_id" int8, primary key ("id"));
     create table "conference" ("id"  bigserial not null, "token" varchar(255), "user_id" int4, primary key ("id"));
     create table "matchable_answer_option" ("id"  bigserial not null, "key_id" int8, "matchable_question_id" int8, "value_id" int8, primary key ("id"));
     create table "passed_matchable_answer" ("id"  bigserial not null, "is_right" boolean, "key_id" int8, "passed_matchable_question_id" int8, "value_id" int8, primary key ("id"));
     create table "passed_question_data" ("passed_question_discriminator" varchar(31) not null, "id"  bigserial not null, "passed_test_id" int8, "question_data_id" int8, primary key ("id"));
     create table "passed_selectable_answer" ("id"  bigserial not null, "is_right" boolean, "passed_selectable_question_id" int8, "user_answer_id" int8, primary key ("id"));
     create table "passed_testn" ("id"  bigserial not null, "passed_at" timestamp, "rating_in_percent" int4, "theme_id" int8, "user_id" int4, primary key ("id"));
     create table "passed_writeable_answer" ("id"  bigserial not null, "is_right" boolean, "user_answer" varchar(255), "passed_writeable_question_id" int8, primary key ("id"));
     create table "question" ("id"  bigserial not null, "complexity" float8, "question" varchar(255), "question_type" int4, "subject_id" int8, "theme_id" int8, primary key ("id"));
     create table "question_data" ("question_discriminator" varchar(31) not null, "id"  bigserial not null, "complexity" float8, "question" varchar(255), "question_type" int4, "subject_id" int8, "theme_id" int8, primary key ("id"));
     create table "question_data_user_files" ("question_data_id" int8 not null, "user_files_id" int8 not null, primary key ("question_data_id", "user_files_id"));
     create table "question_user_files" ("question_id" int8 not null, "user_files_id" int8 not null, primary key ("question_id", "user_files_id"));
     create table "writeable_answer_option" ("id"  bigserial not null, "answer" varchar(255), "is_strict" boolean, "writeable_question_id" int8, primary key ("id"));
     create table "conference_group" ("conference_id" int8 not null, "group_id" int8 not null, primary key ("conference_id", "group_id"));
     create table "departments" ("id"  bigserial not null, "created_at" timestamp, "full_name" varchar(255), "short_name" varchar(255), "updated_at" timestamp, "faculty_id" int8, primary key ("id"));
     create table "faculties" ("id"  bigserial not null, "created_at" timestamp, "full_name" varchar(255), "short_name" varchar(255), "updated_at" timestamp, primary key ("id"));
     create table "file_literature" ("literature_id" int8 not null, "file_id" int8 not null, primary key ("literature_id", "file_id"));
     create table "file_task" ("task_id" int8 not null, "file_id" int8 not null, primary key ("task_id", "file_id"));
     create table "file_work" ("work_id" int8 not null, "file_id" int8 not null, primary key ("work_id", "file_id"));
     create table "files" ("id"  bigserial not null, "access_level" int4, "content_length" int8, "content_type" varchar(255), "link_count" int4, "name" varchar(50), "user_id" int4, primary key ("id"));
     create table "journals" ("id"  bigserial not null, "created_by" int4, "created_date" timestamp, "last_modified_by" int4, "last_modified_date" timestamp, "comment" varchar(255), "lesson_date" timestamp, "study_group_id" int8, "subject_semester_id" int8, "teacher_id" int4, primary key ("id"));
     create table "literature" ("id"  bigserial not null, "authors" varchar(50), "description" varchar(1000000), "title" varchar(50), "type" int4, "user_id" int4, primary key ("id"));
     create table "literature_semester" ("literature_id" int8 not null, "semester_id" int8 not null, primary key ("literature_id", "semester_id"));
     create table "passed_questions" ("id"  bigserial not null, "passed_test_id" int8, "question_id" int8, primary key ("id"));
     create table "passed_tests" ("id"  bigserial not null, "passed_at" timestamp, "rating_in_percent" int4, "theme_id" int8, "user_id" int4, primary key ("id"));
     create table "schedule" ("id"  bigserial not null, "info" varchar(255), "name_group" varchar(255), "number_day" int4, "number_week" int4, "pair_number" int4, "place" varchar(255), "subgroup" int4, "subject" varchar(255), "teacher" varchar(255), "type_subject" int4, primary key ("id"));
     create table "semester_marks" ("id"  bigserial not null, "mark" int4, "subject_semester_id" int8, "user_id" int4, primary key ("id"));
     create table "study_groups" ("id"  bigserial not null, "code" int4, "created_at" timestamp, "full_name" varchar(50), "group_number" int4, "number_of_semester" int4, "short_name" varchar(50), "updated_at" timestamp, "year_of_study_start" int4, "department_id" int8, primary key ("id"));
     create table "subject_semesters" ("id"  bigserial not null, "control_type" int4, "created_at" timestamp, "has_course_project" boolean, "has_course_work" boolean, "name" varchar(255), "updated_at" timestamp, "study_group_id" int8, "subject_id" int8, primary key ("id"));
     create table "subjects" ("id"  bigserial not null, "created_at" timestamp, "decryption" varchar(1000000), "name" varchar(50), "updated_at" timestamp, "department_id" int8, primary key ("id"));
     create table "task_semester" ("task_id" int8 not null, "semester_id" int8 not null, primary key ("task_id", "semester_id"));
     create table "tasks" ("id"  bigserial not null, "description" varchar(1000000), "title" varchar(50), "type" int4, "user_id" int4, primary key ("id"));
     create table "teacher_subject" ("user_id" int4 not null, "subject_id" int8 not null, primary key ("subject_id", "user_id"));
     create table "themes" ("id"  bigserial not null, "attempt_number_in_test" int4, "created_at" timestamp, "decryption" varchar(255), "name" varchar(255), "question_quantity_in_test" int4, "updated_at" timestamp, "subject_id" int8, primary key ("id"));
     create table "user_answers" ("id"  bigserial not null, "answer" varchar(255), "is_right" boolean, "passed_question_id" int8, primary key ("id"));
     create table "user_contacts" ("id"  bigserial not null, "type" varchar(255), "value" varchar(255), "user_id" int4, primary key ("id"));
     create table "users" ("id"  serial not null, "email" varchar(255), "first_name" varchar(255), "last_name" varchar(255), "login" varchar(255), "password" varchar(255), "patronymic" varchar(255), "phone" varchar(255), "role" int4, "avatar_id" int8, "department_id" int8, "study_group_id" int8, primary key ("id"));
     create table "visits" ("id"  bigserial not null, "created_by" int4, "created_date" timestamp, "last_modified_by" int4, "last_modified_date" timestamp, "comment" varchar(255), "is_visited" boolean, "journal_id" int8, "user_id" int4, primary key ("id"));
     create table "works" ("id"  bigserial not null, "created_at" timestamp, "mark" int4, "student_comment" varchar(1000000), "teacher_comment" varchar(1000000), "updated_at" timestamp, "task_id" int8, "user_id" int4, primary key ("id"));
     alter table "question_data_user_files" add constraint UK_hm8yedkntukrf84ijo6vqdick unique ("user_files_id");
     alter table "question_user_files" add constraint UK_6oh4clhdofsmggjecevjwi1xe unique ("user_files_id");
     alter table "answer_choice" add constraint "FKato6fndoobpapfedw7tra8md2" foreign key ("question_id") references "question";
     alter table "answer_option" add constraint "FKjkdibg9f7jteomksecbljfpwo" foreign key ("file_id") references "files";
     alter table "answer_option" add constraint "FKcl0ewlyjlhxuh7c30dm3qck8n" foreign key ("selectable_question_id") references "question_data";
     alter table "conference" add constraint "FK27ftffvfa3f8x9fvrbhitncn8" foreign key ("user_id") references "users";
     alter table "matchable_answer_option" add constraint "FKkmas5mbg7mn3ggocr3v98fxp8" foreign key ("key_id") references "answer_option";
     alter table "matchable_answer_option" add constraint "FKli9obwfca6ipc1alllac4dj3u" foreign key ("matchable_question_id") references "question_data";
     alter table "matchable_answer_option" add constraint "FK11nnl4okl7vsuq62392ay02i7" foreign key ("value_id") references "answer_option";
     alter table "passed_matchable_answer" add constraint "FKtq0hl1lbcm3ofsno5v6t8ehsu" foreign key ("key_id") references "answer_option";
     alter table "passed_matchable_answer" add constraint "FKi1hsbu25bk46ks6s7g83murxb" foreign key ("passed_matchable_question_id") references "passed_question_data";
     alter table "passed_matchable_answer" add constraint "FKkvxxyst5v8otm0ksnsrrtg56p" foreign key ("value_id") references "answer_option";
     alter table "passed_question_data" add constraint "FKjrq1jvptlv18jwmvcegi5b26a" foreign key ("passed_test_id") references "passed_testn";
     alter table "passed_question_data" add constraint "FKgfs6sohus6eyhv4ool4vya12h" foreign key ("question_data_id") references "question_data";
     alter table "passed_selectable_answer" add constraint "FK5xrd3wkkgyqa7l0v5tw04ni0a" foreign key ("passed_selectable_question_id") references "passed_question_data";
     alter table "passed_selectable_answer" add constraint "FKho5b8rpj7ocyw86gdgeq9dnqt" foreign key ("user_answer_id") references "answer_option";
     alter table "passed_testn" add constraint "FKlckfy7mgtt7al0vm1f2xlbkm3" foreign key ("theme_id") references "themes";
     alter table "passed_testn" add constraint "FK5baihg6undnpt7crn22m0djfy" foreign key ("user_id") references "users";
     alter table "passed_writeable_answer" add constraint "FK2gljutru34fmcx4gl92eyasud" foreign key ("passed_writeable_question_id") references "passed_question_data";
     alter table "question" add constraint "FKpghg1kiitomybfduse6wwx90h" foreign key ("subject_id") references "subjects";
     alter table "question" add constraint "FKaue9153g00pml9q8dk0remvmp" foreign key ("theme_id") references "themes";
     alter table "question_data" add constraint "FK3fjypj70dqj76x3hhb4w05vuj" foreign key ("subject_id") references "subjects";
     alter table "question_data" add constraint "FKp64m1jeg6et82kx5a96qlqxbq" foreign key ("theme_id") references "themes";
     alter table "question_data_user_files" add constraint "FKr3k8nug04x1uk9s8mq513y4cc" foreign key ("user_files_id") references "files";
     alter table "question_data_user_files" add constraint "FKtgihvg23xgih2tsko7k9hufg1" foreign key ("question_data_id") references "question_data";
     alter table "question_user_files" add constraint "FKm3p9nxy7yfk45wjx6ie2wfjtl" foreign key ("user_files_id") references "files";
     alter table "question_user_files" add constraint "FKf74xky0knal8rf4gw3u27wytq" foreign key ("question_id") references "question";
     alter table "writeable_answer_option" add constraint "FK61axo4lb6u8q8gn5qq7uwd6vf" foreign key ("writeable_question_id") references "question_data";
     alter table "conference_group" add constraint "FKlu06qq185o1l7rujhy9ow6pj7" foreign key ("group_id") references "study_groups";
     alter table "conference_group" add constraint "FKibapaprc979sjo6jgi8m6p6ey" foreign key ("conference_id") references "conference";
     alter table "departments" add constraint "FK3s8plsoc0lynf5j8oi7b5q3u1" foreign key ("faculty_id") references "faculties";
     alter table "file_literature" add constraint "FKhldya1kk0qfkuiwbcb7ccrlbb" foreign key ("file_id") references "files";
     alter table "file_literature" add constraint "FKt0ml5dgjje6mv7dk3nj6txs1a" foreign key ("literature_id") references "literature";
     alter table "file_task" add constraint "FKkrjyfxyaop481ewgttodtq6ms" foreign key ("file_id") references "files";
     alter table "file_task" add constraint "FK2mm04whgfrrb5oaepnl7esfcx" foreign key ("task_id") references "tasks";
     alter table "file_work" add constraint "FKfi0i3erlnngps3yws6v2fe4en" foreign key ("file_id") references "files";
     alter table "file_work" add constraint "FKe428aymy2at6fh1id6d269dq0" foreign key ("work_id") references "works";
     alter table "files" add constraint "FK70hxcygu4axwbhpdfap1scoxe" foreign key ("user_id") references "users";
     alter table "journals" add constraint "FK2vf3g6qjct1fild6ldulbe9ff" foreign key ("study_group_id") references "study_groups";
     alter table "journals" add constraint "FK5m74q624tig3804q7gh5vr50p" foreign key ("subject_semester_id") references "subject_semesters";
     alter table "journals" add constraint "FKgp9e2fvvy6ih999pgrww5ro2e" foreign key ("teacher_id") references "users";
     alter table "literature" add constraint "FKt8sgjiyivto8diw5ojj19jd02" foreign key ("user_id") references "users";
     alter table "literature_semester" add constraint "FK3b5hu2crc2q5qw84jeff9f5ll" foreign key ("semester_id") references "subject_semesters";
     alter table "literature_semester" add constraint "FKmjv9r2laxjiy9brqpgpq1offq" foreign key ("literature_id") references "literature";
     alter table "passed_questions" add constraint "FKqxpxdaungecgp1npdix6chvb3" foreign key ("passed_test_id") references "passed_tests";
     alter table "passed_questions" add constraint "FKd4l0tf335wf0csc1sbkwuvhh5" foreign key ("question_id") references "question";
     alter table "passed_tests" add constraint "FK8dphk16x4382ahrl2mepha0p9" foreign key ("theme_id") references "themes";
     alter table "passed_tests" add constraint "FKdgr5nljms5odqiikj03994ykv" foreign key ("user_id") references "users";
     alter table "semester_marks" add constraint "FK81umjvayuu43t56amn254uxp8" foreign key ("subject_semester_id") references "subject_semesters";
     alter table "semester_marks" add constraint "FK921spfn5gxq0ar7vqxyrojaoy" foreign key ("user_id") references "users";
     alter table "study_groups" add constraint "FKa4185p8ec0p20m9ueib2ddbe9" foreign key ("department_id") references "departments";
     alter table "subject_semesters" add constraint "FK7tns2hdifij2p08dt29uc8yob" foreign key ("study_group_id") references "study_groups";
     alter table "subject_semesters" add constraint "FKo3gq9snplsuyy8fw3kh0jetli" foreign key ("subject_id") references "subjects";
     alter table "subjects" add constraint "FK87octokwu9ksi3l68kcrdwy8q" foreign key ("department_id") references "departments";
     alter table "task_semester" add constraint "FKqsjo8wlx0p7364p21ihbr15af" foreign key ("semester_id") references "subject_semesters";
     alter table "task_semester" add constraint "FK3gl547chhe9sxcof4h4bive84" foreign key ("task_id") references "tasks";
     alter table "tasks" add constraint "FKerjv7s3gg14l9r6fug99597vq" foreign key ("user_id") references "users";
     alter table "teacher_subject" add constraint "FKgsge0u48er7ky9qg0q74jkfgx" foreign key ("subject_id") references "subjects";
     alter table "teacher_subject" add constraint "FKnhlcm26sl25yqdk3ckpycq2m4" foreign key ("user_id") references "users";
     alter table "themes" add constraint "FKk9y3oaalhtb2wx2482rmc3gp9" foreign key ("subject_id") references "subjects";
     alter table "user_answers" add constraint "FK3elt386mn0ah1cnra6fr8k76p" foreign key ("passed_question_id") references "passed_questions";
     alter table "user_contacts" add constraint "FKhsqvk5ml7ct8a311dxsjw7e6s" foreign key ("user_id") references "users";
     alter table "users" add constraint "FKtakrrot3uqa3pk5e0jq6lh4ih" foreign key ("avatar_id") references "files";
     alter table "users" add constraint "FKowjuwfuaig2ujr9bqqvcl336t" foreign key ("department_id") references "departments";
     alter table "users" add constraint "FKmvx1us0w8j1d3w5252vnjd1u" foreign key ("study_group_id") references "study_groups";
     alter table "visits" add constraint "FK8x11v1wv53xudrvvixgxq0hd5" foreign key ("journal_id") references "journals";
     alter table "visits" add constraint "FK3d546jvep8uy0qq1q6wom2imt" foreign key ("user_id") references "users";
     alter table "works" add constraint "FKf0vrd9bu8xax6y5g4w70gg405" foreign key ("task_id") references "tasks";
     alter table "works" add constraint "FKmj239ekx8jm4krh6yp9pm5mqc" foreign key ("user_id") references "users"

*/
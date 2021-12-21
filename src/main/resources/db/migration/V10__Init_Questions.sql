/* АЛМ/ТА **/
/*1 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Какие виды автоматов Вы знаете?', 0, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Конечные автоматы'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Мили', TRUE, (select id from question where question like 'Какие виды автоматов Вы знаете?'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Мура', TRUE, (select id from question where question like 'Какие виды автоматов Вы знаете?'));
insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Мули', FALSE, (select id from question where question like 'Какие виды автоматов Вы знаете?'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Мира', FALSE, (select id from question where question like 'Какие виды автоматов Вы знаете?'));

/*2 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Какие виды переходов Вы знаете?', 0, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Конечные автоматы'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Условные', TRUE, (select id from question where question like 'Какие виды переходов Вы знаете?'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Безусловные', TRUE, (select id from question where question like 'Какие виды переходов Вы знаете?'));
insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Простые', FALSE, (select id from question where question like 'Какие виды переходов Вы знаете?'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Сложные', FALSE, (select id from question where question like 'Какие виды переходов Вы знаете?'));

/*3 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'У какого автомата состояния проставляются после операторных вершин?', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Конечные автоматы'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Мили', TRUE, (select id
                       from question
                       where question like 'У какого автомата состояния проставляются после операторных вершин?'));

/*4 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Автомат, состояния которого проставляются в операторных вершинах', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Конечные автоматы'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Мура', TRUE, (select id
                       from question
                       where question like 'Автомат, состояния которого проставляются в операторных вершинах'));

/*5 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Основные элементы граф-схемы автомата', 0, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Конечные автоматы'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Дуги', TRUE, (select id from question where question like 'Основные элементы граф-схемы автомата'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Вершины', TRUE, (select id from question where question like 'Основные элементы граф-схемы автомата'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Ромбики', FALSE, (select id from question where question like 'Основные элементы граф-схемы автомата'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Квадратики', FALSE, (select id from question where question like 'Основные элементы граф-схемы автомата'));

/*6 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Как называется функция, переменные которой могу принимать значения либо 0, либо 1?', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Логическая', TRUE, (select id
                             from question
                             where question like
                                   'Как называется функция, переменные которой могу принимать значения либо 0, либо 1?'));

/*7 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Логическая функция, которая преобразует 0 в 1 и наоборот?', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Отрицание', TRUE,
        (select id from question where question like 'Логическая функция, которая преобразует 0 в 1 и наоборот?'));

/*8 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Логическая функция, которая дает единицу, когда только оба операнда 1, в остальных случаях 0', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('И', TRUE, (select id
                    from question
                    where question like 'Логическая функция, которая дает единицу, когда только оба операнда 1, в остальных случаях 0'));
/*9 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Логическая функция, которая дает 0, когда только оба операнда 0, в остальных слуаях 1', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Или', TRUE, (select id
                      from question
                      where question like 'Логическая функция, которая дает 0, когда только оба операнда 0, в остальных слуаях 1'));

/*10 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Логическая функция, которая дает 0, когда оба операнда одинаковые', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Исключающее или', TRUE, (select id
                                  from question
                                  where question like
                                        'Логическая функция, которая дает 0, когда оба операнда одинаковые'));


/*11 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Один из методов минимизации лог.функций: карты ...?', 1, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Минимизация логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Карно', TRUE,
        (select id from question where question like 'Один из методов минимизации лог.функций: карты ...?'));

/*12 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3,
        'Таблица, где представлены всевозможные наборы операндов и всевозможные соотвествуюшие значения лог. функции ...?',
        1, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Минимизация логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('истинности', TRUE, (select id
                             from question
                             where question like
                                   'Таблица, где представлены всевозможные наборы операндов и всевозможные соотвествуюшие значения лог. функции ...?'));

/*13 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3,
        'Метод минимизации, использующий операции попарного неполного склеивания и элементарного поглощения (Фамилия)',
        1, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Минимизация логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('Квайна', TRUE, (select id
                         from question
                         where question like
                               'Метод минимизации, использующий операции попарного неполного склеивания и элементарного поглощения (Фамилия)'));

/*14 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Результат логической функции х1 И х2 = 1, чему равны операнды? (Число)', 1,
        (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Минимизация логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('1', TRUE, (select id
                    from question
                    where question like 'Результат логической функции х1 И х2 = 1, чему равны операнды? (Число)'));

/*15 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Конъюнкция это логическое ...?', 1, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Минимизация логические функции'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('И', TRUE, (select id from question where question like 'Конъюнкция это логическое ...?'));
/*****************/
/*16 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3,
        'Главная часть аппаратного обеспечения компьютера или программируемого логического контроллера это центральный ...?',
        1, (select id from subjects where name like 'Архитектура процессоров'),
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('процессор', TRUE, (select id
                            from question
                            where question like
                                  'Главная часть аппаратного обеспечения компьютера или программируемого логического контроллера это центральный ...?'));

/*17 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, '16 битный аккумуляторный регистр', 1,
        (select id from subjects where name like 'Архитектура процессоров'),
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('AX', TRUE,
        (select id from question where question like '16 битный аккумуляторный регистр'));

/*18 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, '16 битный регистр, исопльзуемый в команде loop', 1,
        (select id from subjects where name like 'Архитектура процессоров'),
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('CX', TRUE,
        (select id from question where question like '16 битный регистр, исопльзуемый в команде loop'));

/*19 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3,
        'Когда запрашиваемые данные отсутствуют в кэше и их нужно подгружать из основного источника называется кэш ...',
        1, (select id from subjects where name like 'Архитектура процессоров'),
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('промах', TRUE, (select id
                         from question
                         where question like
                               'Когда запрашиваемые данные отсутствуют в кэше и их нужно подгружать из основного источника называется кэш ...'));
/*20 question*/
insert
into "question"
("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Английская аббревиатура центрального процессора', 1,
        (select id from subjects where name like 'Архитектура процессоров'),
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
("answer", "is_right", "question_id")
values ('CPU', TRUE, (select id from question where question like 'Английская аббревиатура центрального процессора'));

/*------------------ new testing system migration -----------------------------------------------------*/

/*21 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq', 3, 'Выберите конечные детерминирование автоматы', 0,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Конечные автоматы'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Мили', true,
        (select id from question_data where question_data.question like 'Выберите конечные детерминирование автоматы'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Мура', true,
        (select id from question_data where question_data.question like 'Выберите конечные детерминирование автоматы'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('МиЫра', false,
        (select id from question_data where question_data.question like 'Выберите конечные детерминирование автоматы'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Мyли', false,
        (select id from question_data where question_data.question like 'Выберите конечные детерминирование автоматы'));


/*22 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('write', 3, 'Автомат, у которого состояния проставляются после операторных вершин?', 1,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Конечные автоматы'));
insert
into writeable_answer_option
(answer, is_strict, writeable_question_id)
values ('Мили', false, ((select id
                         from question_data
                         where question like 'Автомат, у которого состояния проставляются после операторных вершин?')));
insert
into writeable_answer_option
(answer, is_strict, writeable_question_id)
values ('автомат мили', false, (select id
                                from question_data
                                where question like
                                      'Автомат, у которого состояния проставляются после операторных вершин?'));

/*23 question*/
insert into question_data (question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('match', 3, 'Сопоставьте элементы блок-схемы', 2, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Конечные автоматы'));
insert
into answer_option (answer)
values ('овал');
insert
into answer_option (answer)
values ('начало');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'овал'),
        (select id from question_data where question like 'Сопоставьте элементы блок-схемы'),
        (select id from answer_option where answer like 'начало'));

insert
into answer_option (answer)
values ('ромб');
insert
into answer_option (answer)
values ('условие');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'ромб'),
        (select id from question_data where question like 'Сопоставьте элементы блок-схемы'),
        (select id from answer_option where answer like 'условие'));


/*24 question*/
insert into question_data (question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('match', 3, 'Сопоставьте элементы граф-схемы автомата', 2, (select id from subjects where name like 'АЛМ'),
        (select id from themes where name like 'Конечные автоматы'));

insert
into answer_option (answer)
values ('вершина');
insert
into answer_option (answer)
values ('состояние');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'вершина'),
        (select id from question_data where question like 'Сопоставьте элементы граф-схемы автомата'),
        (select id from answer_option where answer like 'состояние'));

insert
into answer_option (answer)
values ('переход');
insert
into answer_option (answer)
values ('дуга');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'переход'),
        (select id from question_data where question like 'Сопоставьте элементы граф-схемы автомата'),
        (select id from answer_option where answer like 'дуга'));

/*25 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('write', 3, 'Автомат, у которого состояния проставляются на операторных вершинах?', 1,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Конечные автоматы'));
insert
into writeable_answer_option
(answer, is_strict, writeable_question_id)
values ('Мура', false, ((select id
                         from question_data
                         where question like 'Автомат, у которого состояния проставляются на операторных вершинах?')));
insert
into writeable_answer_option
(answer, is_strict, writeable_question_id)
values ('автомат мура', false, ((select id
                                 from question_data
                                 where question like
                                       'Автомат, у которого состояния проставляются на операторных вершинах?')));

/*26 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq', 3, 'Последовательность программы', 3,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Конечные автоматы'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('начало', true,
        (select id from question_data where question_data.question like 'Последовательность программы'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('блок логики', true,
        (select id from question_data where question_data.question like 'Последовательность программы'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('конец', true,
        (select id from question_data where question_data.question like 'Последовательность программы'));

/*27 question */
insert into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq',3,'Порядок кэша в процессоре по объему от меньшего к большему', 3,
        (select id from subjects where name like 'Архитектура процессоров'), (select id from themes where name like 'Процессоры'));

insert
into answer_option (answer, is_right, selectable_question_id)
values ('L1', true,
        (select id from question_data where question_data.question like 'Порядок кэша в процессоре по объему от меньшего к большему'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('L2', true,
        (select id from question_data where question_data.question like 'Порядок кэша в процессоре по объему от меньшего к большему'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('L3', true,
        (select id from question_data where question_data.question like 'Порядок кэша в процессоре по объему от меньшего к большему'));


/*28 question */
insert into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq',3,'Скорость работы памяти от медленной к быстрой', 3,
        (select id from subjects where name like 'Архитектура процессоров'), (select id from themes where name like 'Процессоры'));

insert
into answer_option (answer, is_right, selectable_question_id)
values ('Жесткий диск', true,
        (select id from question_data where question_data.question like 'Скорость работы памяти от медленной к быстрой'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Оперативная память', true,
        (select id from question_data where question_data.question like 'Скорость работы памяти от медленной к быстрой'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Кэш процессора', true,
        (select id from question_data where question_data.question like 'Скорость работы памяти от медленной к быстрой'));

/*29 question */
insert into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq',3,'Как называется процессор, имеюший два или более ядра', 0,
        (select id from subjects where name like 'Архитектура процессоров'), (select id from themes where name like 'Процессоры'));

insert
into answer_option (answer, is_right, selectable_question_id)
values ('Многоядерный', true,
        (select id from question_data where question_data.question like 'Как называется процессор, имеюший два или более ядра'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Многосложный', false,
        (select id from question_data where question_data.question like 'Как называется процессор, имеюший два или более ядра'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Многопроцессорный', false,
        (select id from question_data where question_data.question like 'Как называется процессор, имеюший два или более ядра'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('Многоуровневый', false,
        (select id from question_data where question_data.question like 'Как называется процессор, имеюший два или более ядра'));

/*30 question */
insert into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq',3,'Регистр, используемый в качестве счетчика в команде loop', 0,
        (select id from subjects where name like 'Архитектура процессоров'), (select id from themes where name like 'Процессоры'));

insert
into answer_option (answer, is_right, selectable_question_id)
values ('CX', true,
        (select id from question_data where question_data.question like 'Регистр, используемый в качестве счетчика в команде loop'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('AX', false,
        (select id from question_data where question_data.question like 'Регистр, используемый в качестве счетчика в команде loop'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('BX', false,
        (select id from question_data where question_data.question like 'Регистр, используемый в качестве счетчика в команде loop'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('DX', false,
        (select id from question_data where question_data.question like 'Регистр, используемый в качестве счетчика в команде loop'));

/*31 question*/
insert into question_data (question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('match', 3, 'Сопоставьте название команд и их действия', 2, (select id from subjects where name like 'Архитектура процессоров'),
        (select id from themes where name like 'Процессоры'));
insert
into answer_option (answer)
values ('jmp');
insert
into answer_option (answer)
values ('безусловный переход');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'jmp'),
        (select id from question_data where question like 'Сопоставьте название команд и их действия'),
        (select id from answer_option where answer like 'безусловный переход'));
insert
into answer_option (answer)
values ('cmp');
insert
into answer_option (answer)
values ('сравнить два числа');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'cmp'),
        (select id from question_data where question like 'Сопоставьте название команд и их действия'),
        (select id from answer_option where answer like 'сравнить два числа'));
insert
into answer_option (answer)
values ('mov');
insert
into answer_option (answer)
values ('переместить значение');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'mov'),
        (select id from question_data where question like 'Сопоставьте название команд и их действия'),
        (select id from answer_option where answer like 'переместить значение'));


/*32 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('write', 3, 'Английская аббревиатура центрального процессора', 1,
           (select id from subjects where name like 'Архитектура процессоров'),
       (select id from themes where name like 'Процессоры'));
insert
into writeable_answer_option
(answer, is_strict, writeable_question_id)
values ('cpu', false, ((select id
                         from question_data
                         where question like 'Английская аббревиатура центрального процессора')));


/*32 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq', 3, 'Логическая функция, преобразующая 0 в 1 и наоборот', 0,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Логические функции'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('отрицание', true,
        (select id from question_data where question_data.question like 'Логическая функция, преобразующая 0 в 1 и наоборот'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('и', false,
        (select id from question_data where question_data.question like 'Логическая функция, преобразующая 0 в 1 и наоборот'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('или', false,
        (select id from question_data where question_data.question like 'Логическая функция, преобразующая 0 в 1 и наоборот'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('исключающее или', false,
        (select id from question_data where question_data.question like 'Логическая функция, преобразующая 0 в 1 и наоборот'));


/*33 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq', 3, 'Логическая функция, которая дает единицу, когда только оба операнда 1, в остальных случаях 0', 0,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Логические функции'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('отрицание', false,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает единицу, когда только оба операнда 1, в остальных случаях 0'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('и', true,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает единицу, когда только оба операнда 1, в остальных случаях 0'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('или', false,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает единицу, когда только оба операнда 1, в остальных случаях 0'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('исключающее или', false,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает единицу, когда только оба операнда 1, в остальных случаях 0'));


/*34 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq', 3, 'Логическая функция, которая дает 0, когда только оба операнда 0, в остальных слуаях 1', 0,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Логические функции'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('отрицание', false,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает 0, когда только оба операнда 0, в остальных слуаях 1'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('и', false,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает 0, когда только оба операнда 0, в остальных слуаях 1'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('или', true,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает 0, когда только оба операнда 0, в остальных слуаях 1'));
insert
into answer_option (answer, is_right, selectable_question_id)
values ('исключающее или', false,
        (select id from question_data where question_data.question like 'Логическая функция, которая дает 0, когда только оба операнда 0, в остальных слуаях 1'));


/*35 question*/
insert
into question_data
(question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('choose_and_seq', 3, 'Логическая функция, которая дает 0, когда только оба операнда равный друг другу, в остальных слуаях 1', 1,
        (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Логические функции'));

insert
into writeable_answer_option
(answer, is_strict, writeable_question_id)
values ('xor', false, ((select id
                        from question_data
                        where question like 'Логическая функция, которая дает 0, когда только оба операнда равный друг другу, в остальных слуаях 1')));

insert
into writeable_answer_option
(answer, is_strict, writeable_question_id)
values ('исключающее или', false, ((select id
                        from question_data
                        where question like 'Логическая функция, которая дает 0, когда только оба операнда равный друг другу, в остальных слуаях 1')));


/*36 question*/
insert into question_data (question_discriminator, complexity, question, question_type, subject_id, theme_id)
values ('match', 3, 'Сопоставьте логическую операцию с ее названием', 2, (select id from subjects where name like 'АЛМ'), (select id from themes where name like 'Логические функции'));
insert
into answer_option (answer)
values ('A&&B');
insert
into answer_option (answer)
values ('логическое и');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'A&&B'),
        (select id from question_data where question like 'Сопоставьте логическую операцию с ее названием'),
        (select id from answer_option where answer like 'логическое и'));
insert
into answer_option (answer)
values ('A||B');
insert
into answer_option (answer)
values ('логическое или');
insert into matchable_answer_option (key_id, matchable_question_id, value_id)
values ((select id from answer_option where answer like 'A||B'),
        (select id from question_data where question like 'Сопоставьте логическую операцию с ее названием'),
        (select id from answer_option where answer like 'логическое или'));

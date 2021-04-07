/* АЛМ/ТА **/
/*1 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Какие виды автоматов Вы знаете?', 0, 2, (select id from themes where name like 'Конечные автоматы'));

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
values (3, 'Какие виды переходов Вы знаете?', 0, 2, (select id from themes where name like 'Конечные автоматы'));

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
values (3, 'У какого автомата состояния проставляются после операторных вершин?', 1, 2,
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
values (3, 'Автомат, состояния которого проставляются в операторных вершинах', 1, 2,
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
values (3, 'Основные элементы граф-схемы автомата', 0, 2, (select id from themes where name like 'Конечные автоматы'));

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
values ('Ромбики', TRUE, (select id from question where question like 'Основные элементы граф-схемы автомата'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('Квадратики', TRUE, (select id from question where question like 'Основные элементы граф-схемы автомата'));

/*6 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Как называется функция, переменные которой могу принимать значения либо 0, либо 1?', 1, 2,
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
values (3, 'Логическая функция, которая преобразует 0 в 1 и наоборот?', 1, 2,
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('Отриацание', TRUE,
        (select id from question where question like 'Логическая функция, которая преобразует 0 в 1 и наоборот?'));

/*8 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Логическая функция, которая дает единицу, когда только оба операнда 1', 1, 2,
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('И', TRUE, (select id
                    from question
                    where question like 'Логическая функция, которая дает единицу, когда только оба операнда 1'));
/*9 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Логическая функция, которая дает 0, когда только оба операнда 0', 1, 2,
        (select id from themes where name like 'Логические функции'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('Или', TRUE, (select id
                      from question
                      where question like 'Логическая функция, которая дает 0, когда только оба операнда 0'));

/*10 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, 'Логическая функция, которая дает 0, когда оба операнда одинаковые', 1, 2,
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
values (3, 'Один из методов минимизации лог.функций: карты ...?', 1, 2,
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
        1, 2, (select id from themes where name like 'Минимизация логические функции'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('Истинности', TRUE, (select id
                             from question
                             where question like
                                   'Таблица, где представлены всевозможные наборы операндов и всевозможные соотвествуюшие значения лог. функции ...?'));

/*13 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3,
        'Метод минимизации, использующий операции попарного неполного склеивания и элементарного поглощения (Фамилия)',
        1, 2, (select id from themes where name like 'Минимизация логические функции'));

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
values (3, 'Результат логической функции х1 И х2 = 1, чему равны операнды? (Число)', 1, 2,
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
values (3, 'Конъюнкция это логическое ...?', 1, 2,
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
        1, 1, (select id from themes where name like 'Процессоры'));

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
values (3, '16 битный аккумуляторный регистр (большими буквами)', 1, 1,
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('AX', TRUE,
        (select id from question where question like '16 битный аккумуляторный регистр (большими буквами)'));

/*18 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3, '16 битный регистр, исопльзуемый в команде loop (большими буквами)', 1, 1,
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('CX', TRUE,
        (select id from question where question like '16 битный аккумуляторный регистр (большими буквами)'));

/*19 question*/
insert
into "question"
    ("complexity", "question", "question_type", "subject_id", "theme_id")
values (3,
        'Когда запрашиваемые данные отсутствуют в кэше и их нужно подгружать из основного источника называется кэш ...',
        1, 1, (select id from themes where name like 'Процессоры'));

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
values (3, 'Английская аббревиатура центрального процессора', 1, 1,
        (select id from themes where name like 'Процессоры'));

insert
into "answer_choice"
    ("answer", "is_right", "question_id")
values ('CPU', TRUE, (select id from question where question like 'Английская аббревиатура центрального процессора'));
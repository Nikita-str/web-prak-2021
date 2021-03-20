INSERT INTO publishers(p_name) VALUES ('first izd'), ('fill i.'), ('second publisher');

/*
DELETE FROM author_book WHERE TRUE;
DELETE FROM authors WHERE TRUE;
DELETE FROM book_ex_history WHERE TRUE;
DELETE FROM book_examples WHERE TRUE;
DELETE FROM books WHERE TRUE;
DELETE FROM author_book WHERE TRUE;
DELETE FROM readers WHERE TRUE;
DELETE FROM publishers WHERE TRUE;

SET ROLE prak_user
SELECT current_user
*/

SELECT add_author('Братья', 'Стругацкие', NULL);
SELECT add_author('Автор', 'Неизвестный', NULL);
SELECT add_author('Автор', 'Вымышленный', NULL);

SELECT add_book('книга о которой известно ничего(кроме названия)');

CALL add_author_to_book(2, 1);
CALL add_author_to_book(3, 1);

CALL add_author_to_book(
		get_author_id('Станислав', 'Лем', 'Герман'),
		add_book('Возвращение со звезд', 
				'Мир, где нет старости и болезней, нет бедности и жестокости... Будет ли такой мир идеальным?', 
				'АСТ', 1961, '978-5-17-093614-4', 6)
	);
CALL add_author_to_book(
	get_author_id('Станислав', 'Лем', 'Герман'),
	add_book('Фиаско', 'последний роман Станислава Лема', 'АСТ', 1986, '978-5-17-069299-6', 4)
	);
CALL add_author_to_book(
	get_author_id('Станислав', 'Лем', 'Герман'),
	add_book('Сумма технологий', 'Классика футурологии.', 'АСТ', 1964, '978-5-17-100637-2', 2)
	);

CALL add_author_to_book(
	get_author_id('Дадзай', 'Оcаму', NULL),
	add_book('исповедь неполноценного человека', NULL, 1964, NULL, 5)
	);

SELECT add_book('Математический анализ в двух частях. Часть 2. Учебник для бакалавров', 
			    'Небольшая часть курса часть курса математического анализа.',
		  	    'Юрайт-Издат', 2013, '978-5-9916-2742-9', 10);

CALL add_author_to_book(get_author_id('Владимир', 'Ильин', 'Александрович'), 6);
CALL add_author_to_book(get_author_id('Благовест', 'Сендов', 'Христов'), 6);
CALL add_author_to_book(get_author_id('Виктор', 'Садовничий', 'Антонович'), 6);

SELECT add_reader('Name', 'Surname', NULL, 'Планета Хаксли, вселенная Оруэлла', '8(800)008');
SELECT add_reader('должник', 'книг', '...', 'где?', '8(16)32-64');
SELECT add_reader('ученик', 'первый', NULL, NULL, '32-32-32');
SELECT add_reader('ученик', 'второй', 'о', 'тут', NULL);
SELECT add_reader('ученик', 'третий', 'а', NULL, '5-5-5');

CALL book_take(20, 3, 30*4);
CALL book_take(21, 4, 31*4);
CALL book_take(22, 5, 30*5);

INSERT INTO book_ex_history(book_ex_id, lib_card_id, date_of_issue, shedule_ret_date, real_ret_date)
VALUES (4, 2, CAST('20.02.2020' AS DATE), CAST('20.03.2020' AS DATE), CAST('25.02.2020' AS DATE)),
	   (4, 2, CAST('20.02.2020' AS DATE), CAST('20.03.2020' AS DATE), CAST('20.04.2020' AS DATE));

CALL book_take(10, 2,  CAST('21.01.2021' AS DATE), CAST('22.01.2021' AS DATE));

CALL book_take(9, 1, 15);
--CALL book_take(8, 1, 15) -- ERROR cause already has the same book (but another examplar) 
CALL book_take(3, 1, 15);
CALL book_ret(9);

/*
SELECT * FROM get_authors_of_book(1)

SELECT * FROM get_ex_book_history(9)

SELECT * FROM get_reader_history(1)
SELECT * FROM get_reader_cur_book(1)

SELECT * FROM get_reader_history(2)
SELECT * FROM get_reader_overdue_book(2, false)
SELECT * FROM get_reader_overdue_book(2, true)
*/

/*
TABLE books;
TABLE authors;
TABLE readers;
TABLE publishers;

TABLE author_book;

TABLE book_examples;

TABLE book_ex_history;
*/





--



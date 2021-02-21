INSERT INTO publishers(p_name) VALUES ('first'), ('fill'), ('second');
--INSERT INTO publishers(p_name) VALUES ('АСТ');

--DELETE FROM authors WHERE TRUE;
--DELETE FROM books WHERE TRUE;
--DELETE FROM author_book WHERE TRUE;

SELECT add_author('Братья', 'Стругацкие', NULL);
SELECT add_author('Автор', 'Неизвестный', NULL);

SELECT add_book('книга о которой известно ничего(кроме названия)');


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

SELECT add_reader('Name', 'Surname', NULL, 'Планета Хаксли, вселенная Оруэлла', '8(800)008');
SELECT add_reader('должник', 'книг', '...', 'где?', '8(16)32-64');

INSERT INTO book_ex_history(book_ex_id, lib_card_id, date_of_issue, shedule_ret_date, real_ret_date)
VALUES (153453, 2, CAST('20.02.2020' AS DATE), CAST('20.03.2020' AS DATE), CAST('25.02.2020' AS DATE)),
	   (153453, 2, CAST('20.02.2020' AS DATE), CAST('20.03.2020' AS DATE), CAST('20.04.2020' AS DATE)),
	   (153461, 2, CAST('20.02.2020' AS DATE), CAST('20.03.2020' AS DATE), NULL);

SELECT * FROM books;
SELECT * FROM authors;
SELECT * FROM author_book;
SELECT * FROM book_examples;

SELECT * FROM readers;

CALL book_take(153460, 1, 15)
CALL book_take(153459, 1, 15)
CALL book_take(153452, 1, 15)
CALL book_ret(153460)

SELECT * FROM get_ex_book_history(153460)

SELECT * FROM get_reader_history(1)
SELECT * FROM get_reader_cur_book(1)

SELECT * FROM get_reader_history(2)
SELECT * FROM get_reader_overdue_book(2, false)


SELECT * FROM book_ex_history

SELECT get_publisher_id('АСТ');

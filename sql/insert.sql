INSERT INTO publishers(p_name) VALUES ('first'), ('fill'), ('second');

INSERT INTO publishers(p_name) VALUES ('АСТ');

--DELETE FROM authors WHERE TRUE;
--DELETE FROM books WHERE TRUE;

SELECT add_author('Братья', 'Стругацкие', NULL);
SELECT add_author('Автор', 'Неизвестный', NULL);

SELECT add_book('книга о которой известно ничего(кроме названия)');


CALL add_author_to_book(
		get_author_id('Станислав', 'Лем', 'Герман'),
		add_book('Возвращение со звезд', 
				'Мир, где нет старости и болезней, нет бедности и жестокости... Будет ли такой мир идеальным?', 
				'АСТ', 1961, '978-5-17-093614-4', 100)
	);

CALL add_author_to_book(
	get_author_id('Станислав', 'Лем', 'Герман'),
	add_book('Фиаско', 'последний роман Станислава Лема', 'АСТ', 1986, '978-5-17-069299-6', 100)
	);

SELECT * FROM books;
SELECT * FROM authors;
SELECT * FROM author_book;

SELECT get_publisher_id('АСТ');
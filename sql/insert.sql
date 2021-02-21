INSERT INTO publishers(p_name) VALUES ('first'), ('fill'), ('second');

INSERT INTO publishers(p_name) VALUES ('АСТ');

--DELETE FROM authors WHERE TRUE;
--DELETE FROM books WHERE TRUE;

SELECT add_author('Станислав', 'Лем', 'Герман');
SELECT add_author('Братья', 'Стругацкие', NULL);
SELECT add_author('Автор', 'Неизвестный', NULL);

SELECT add_book('книга о которой известно ничего(кроме названия)');

SELECT add_book('Фиаско', 'последний роман Станислава Лема', 
				(SELECT p_id FROM publishers WHERE p_name = 'АСТ'),  
				1986, '978-5-17-069299-6', 100);

SELECT add_book('Возвращение со звезд', 
				'Мир, где нет старости и болезней, нет бедности и жестокости... Мир, в котором нет страха. Так ли он хорош?', 
				'АСТ', 1961, '978-5-17-093614-4', 100);

SELECT * FROM books;

SELECT get_publisher_id('АСТ');
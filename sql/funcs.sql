--DROP FUNCTION find_publisher(text, bool)
CREATE OR REPLACE FUNCTION find_publisher(publ_name TEXT, complete_match BOOL)
RETURNS SETOF publishers 
AS $$
DECLARE
	r publishers%rowtype;
BEGIN
	IF complete_match THEN
		FOR r IN
			SELECT * FROM publishers WHERE p_name = publ_name
		LOOP 
			RETURN NEXT r;
		END LOOP;
	ELSE 
		FOR r IN
			SELECT * FROM publishers WHERE p_name SIMILAR TO (publ_name || '%')
		LOOP 
			RETURN NEXT r;
		END LOOP;
	END IF;
	RETURN;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_publisher_id(publ_name TEXT, OUT ret_p_id INTEGER)
AS $$ BEGIN
	ret_p_id = NULL;
	SELECT p_id FROM publishers WHERE p_name = publ_name INTO ret_p_id;
	IF (ret_p_id IS NULL) THEN
		WITH publ AS (INSERT INTO publishers(p_name) VALUES (publ_name) RETURNING p_id)
		SELECT * FROM publ INTO ret_p_id;
	END IF;
END; $$ LANGUAGE plpgsql;
--SELECT * FROM find_publisher('fi', false);


--- START : AUTHORS -------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION find_author(f_name VARCHAR(40), s_name VARCHAR(40), patr VARCHAR(40), complete_match BOOL)
RETURNS SETOF authors 
AS $$
DECLARE
	r authors%rowtype;
BEGIN
	f_name = LOWER(f_name);
	s_name = LOWER(s_name);
	patr = LOWER(patr);
	IF complete_match THEN
		FOR r IN
			SELECT * FROM authors WHERE first_name = f_name AND second_name = s_name AND patronymic = patr
		LOOP 
			RETURN NEXT r;
		END LOOP;
	ELSE 
		FOR r IN
			SELECT * FROM authors WHERE 
				((f_name IS NULL) OR first_name SIMILAR TO (f_name || '%')) AND
				((s_name IS NULL) OR second_name SIMILAR TO (s_name || '%')) AND
				((patr IS NULL) OR (patronymic IS NULL) OR patronymic SIMILAR TO (patr || '%'))
		LOOP 
			RETURN NEXT r;
		END LOOP;
	END IF;
	RETURN;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_author(s0 VARCHAR(40), s1 VARCHAR(40), complete_match BOOL)
RETURNS SETOF authors 
AS $$ DECLARE
	r authors%rowtype;
BEGIN
	FOR r IN 
		SELECT * FROM find_author(s0, s1, NULL, complete_match) 
		UNION
		SELECT * FROM find_author(s1, s0, NULL, complete_match) 
		UNION
		SELECT * FROM find_author(NULL, s1, s0, complete_match) 
		UNION
		SELECT * FROM find_author(NULL, s0, s1, complete_match) 
	LOOP RETURN NEXT r; END LOOP;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_author(surname VARCHAR(40), complete_match BOOL)
RETURNS SETOF authors 
AS $$ DECLARE
	r authors%rowtype;
BEGIN
	FOR r IN SELECT * FROM find_author(NULL, surname, NULL, complete_match) 
	LOOP RETURN NEXT r; END LOOP;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_author(f_name VARCHAR(40), s_name VARCHAR(40), patr VARCHAR(40), OUT ret_author_id INTEGER)
AS $$
BEGIN
	WITH auth AS
	(
	INSERT INTO authors(second_name, first_name, patronymic) VALUES (LOWER(s_name), LOWER(f_name), LOWER(patr))
	RETURNING author_id
	)
	SELECT * FROM auth INTO ret_author_id;
END;
$$
LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE add_author_to_book(author_id INT, book_id INT)
AS $$ BEGIN
INSERT INTO author_book(author_id, book_id) VALUES (author_id, book_id);
END; $$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_author_id(f_name VARCHAR(40), s_name VARCHAR(40), patr VARCHAR(40), OUT ret_author_id INTEGER)
AS $$ BEGIN
	f_name = LOWER(f_name);
	s_name = LOWER(s_name);
	patr = LOWER(patr);
	ret_author_id = NULL;
	SELECT author_id FROM authors WHERE 
		first_name = f_name AND second_name = s_name AND patronymic = patr INTO ret_author_id;
		
	IF (ret_author_id IS NULL) THEN
		SELECT * FROM add_author(f_name, s_name, patr) INTO ret_author_id;
	END IF;
END; $$ LANGUAGE plpgsql;

--- END : AUTHORS -------------------------------------------------------------------------------------------------


--- BOOK EXAMPLE
CREATE OR REPLACE PROCEDURE add_book_ex(bk_id INT, amount INT)
AS $$ BEGIN
	WHILE (amount > 0) LOOP
		INSERT INTO book_examples(book_id) VALUES (bk_id);
		amount = amount - 1;
	END LOOP;	
END; $$ LANGUAGE plpgsql;

--- START : BOOKS -------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION add_book(title TEXT, OUT ret_book_id INTEGER)
AS $$
BEGIN
	WITH bk AS (INSERT INTO books(title) VALUES (LOWER(title)) RETURNING book_id)
	SELECT * FROM bk INTO ret_book_id;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_book(title TEXT, about TEXT, publ_id INT, pub_year INT, ISBN VARCHAR(25), amount INT, OUT ret_book_id INTEGER)
AS $$
BEGIN
	WITH bk AS (
		INSERT INTO books(title, about, publisher_id, pub_year, ISBN) VALUES 
		(LOWER(title), about, publ_id, TO_DATE(pub_year::text, 'YYYY'), ISBN) RETURNING book_id
	)
	SELECT * FROM bk INTO ret_book_id;
	
	CALL add_book_ex(ret_book_id, amount);	
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_book(title TEXT, about TEXT, publ_name TEXT, pub_year INT, ISBN VARCHAR(25), amount INT, OUT ret_book_id INTEGER)
AS $$ BEGIN
	SELECT * FROM add_book(title, about, get_publisher_id(publ_name), pub_year, ISBN, amount) INTO ret_book_id;
END; $$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION add_book(title TEXT, about TEXT, pub_year INT, ISBN VARCHAR(25), amount INT, OUT ret_book_id INTEGER)
AS $$
BEGIN
	WITH bk AS (
		INSERT INTO books(title, about, pub_year, ISBN) VALUES 
		(LOWER(title), about, TO_DATE(pub_year::text, 'YYYY'), ISBN) RETURNING book_id
	)
	SELECT * FROM bk INTO ret_book_id;
	
	CALL add_book_ex(ret_book_id, amount);
END;
$$
LANGUAGE plpgsql;
--- END : BOOKS -------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION add_reader(f_name VARCHAR(40), s_name VARCHAR(40), patr VARCHAR(40), 
									  address TEXT, phone_number VARCHAR(30), OUT ret_reader_id INTEGER)
AS $$ BEGIN
	WITH reader AS
	(
	INSERT INTO readers(second_name, first_name, patronymic, address, phone_number) 
	VALUES (LOWER(s_name), LOWER(f_name), LOWER(patr), LOWER(address), phone_number)
	RETURNING library_card_id
	)
	SELECT * FROM reader INTO ret_reader_id;
END; $$ LANGUAGE plpgsql;

--- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--- BOOK_EX CHECK  -------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION the_same_book(ex_id_1 INT, ex_id_2 INT)
RETURNS BOOL
AS $$ BEGIN
	RETURN  (SELECT book_id FROM book_examples WHERE book_ex_id = ex_id_1) 
			=
			(SELECT book_id FROM book_examples WHERE book_ex_id = ex_id_2);
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION book_already_taked(bk_ex_id INT)
RETURNS BOOL AS $$ BEGIN
	RETURN ((SELECT spare FROM book_examples be WHERE be.book_ex_id = bk_ex_id) = FALSE);
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION book_is_dereg(bk_ex_id INT) --deregistered = decommissioned
RETURNS BOOL AS $$ BEGIN
	RETURN ((SELECT decommissioned FROM book_examples be WHERE be.book_ex_id = bk_ex_id) = TRUE);
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION reader_has_book_ex(bk_ex_id INT, _lib_card_id INT) --deregistered = decommissioned
RETURNS BOOL AS $$ BEGIN
	RETURN ((SELECT COUNT(*) FROM book_ex_history beh 
	    	 WHERE (real_ret_date IS NULL) AND (beh.lib_card_id = _lib_card_id) 
			 AND the_same_book(book_ex_id, bk_ex_id)) <> 0);
END; $$ LANGUAGE plpgsql;

--- BOOK_EX CHECK  -------------------------------------------------------------------------------------------------
--- ----------------------------------------------------------------------------------------------------------------

--- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--- BOOK_EX TAKE / RET  -----------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE book_take(bk_ex_id INT, lib_card_id INT, date_issue DATE, schedule_ret_date DATE)
AS $$ BEGIN
	IF book_already_taked(bk_ex_id) THEN
		RAISE EXCEPTION 'книга уже выдана [book ex. id = %]', bk_ex_id;
	END IF;
	IF book_is_dereg(bk_ex_id) THEN
		RAISE EXCEPTION 'книга снята с учета [book ex. id = %]', bk_ex_id;
	END IF;
	IF reader_has_book_ex(bk_ex_id, lib_card_id) THEN
		RAISE EXCEPTION 'у данного читателя уже есть экземпляр данной книги [book id = %]', bk_ex_id;
	END IF;

	INSERT INTO book_ex_history(book_ex_id, lib_card_id, date_of_issue, shedule_ret_date, real_ret_date) 
	VALUES (bk_ex_id, lib_card_id, date_issue, schedule_ret_date, NULL);

	UPDATE book_examples be SET spare = FALSE WHERE be.book_ex_id = bk_ex_id;		
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE book_take(bk_ex_id INT, lib_card_id INT, schedule_ret_date DATE)
AS $$ BEGIN
	CALL book_take(bk_ex_id, lib_card_id, CAST(NOW() AS DATE), schedule_ret_date);	
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE book_take(book_ex_id INT, lib_card_id INT, day_for_ret INT)
AS $$ BEGIN
	CALL book_take(book_ex_id, lib_card_id, CAST(now() + day_for_ret * interval '1D' AS DATE));
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE book_ret(bk_ex_id INT)
AS $$ BEGIN
	IF NOT book_already_taked(bk_ex_id) THEN 
		RAISE EXCEPTION 'book not taked [book ex. id = %]', bk_ex_id;
	END IF;

	UPDATE book_ex_history beh SET real_ret_date = CAST(NOW() AS DATE) 
	WHERE (real_ret_date IS NULL) AND (beh.book_ex_id = bk_ex_id); 

	UPDATE book_examples be SET spare = TRUE WHERE be.book_ex_id = bk_ex_id;
END; $$ LANGUAGE plpgsql;
--- BOOK_EX TAKE / RET  --------------------------------------------------------------------------------------------
--- ----------------------------------------------------------------------------------------------------------------


--- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--- HISTORY -----------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_ex_book_history(bk_ex_id INT)
RETURNS SETOF book_ex_history 
AS $$ DECLARE
	r book_ex_history%rowtype;
BEGIN
	FOR r IN (SELECT * FROM book_ex_history WHERE book_ex_id = bk_ex_id ORDER BY date_of_issue) LOOP 
		RETURN NEXT r;
	END LOOP;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_reader_history(_lib_card_id INT)
RETURNS SETOF book_ex_history 
AS $$ DECLARE
	r book_ex_history%rowtype;
BEGIN
	FOR r IN (SELECT * FROM book_ex_history WHERE lib_card_id = _lib_card_id ORDER BY date_of_issue) LOOP 
		RETURN NEXT r;
	END LOOP;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_reader_cur_book(_lib_card_id INT)
RETURNS SETOF book_ex_history 
AS $$ DECLARE
	r book_ex_history%rowtype;
BEGIN
	FOR r IN (SELECT * FROM get_reader_history(_lib_card_id) WHERE (real_ret_date IS NULL) ORDER BY shedule_ret_date) LOOP 
		RETURN NEXT r;
	END LOOP;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_reader_overdue_book(_lib_card_id INT, only_cur BOOL)
RETURNS SETOF book_ex_history 
AS $$ DECLARE
	r book_ex_history%rowtype;
BEGIN
	FOR r IN (SELECT * FROM book_ex_history WHERE 
			  lib_card_id = _lib_card_id AND 
			  (((real_ret_date IS NULL) AND shedule_ret_date < CAST(NOW() AS DATE)) 
			   OR shedule_ret_date < real_ret_date) AND ((NOT only_cur) OR real_ret_date IS NULL)
	 		  ORDER BY date_of_issue) 
	 LOOP 
		RETURN NEXT r;
	END LOOP;
END; $$ LANGUAGE plpgsql;
--- HISTORY  -------------------------------------------------------------------------------------------------------
--- ----------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION reader_can_pass_lib_card(_lib_card_id INT)
RETURNS BOOL AS $$ BEGIN
	IF (SELECT lib_card_passed FROM readers WHERE library_card_id = _lib_card_id) THEN RETURN FALSE; END IF;
	RETURN (SELECT COUNT(*) FROM get_reader_overdue_book(_lib_card_id, true)) = 0;
END; $$ LANGUAGE plpgsql;


--- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--- DEREGISTER -----------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE book_ex_dereg(bk_ex_id INT, need_to_ret BOOL)
AS $$ BEGIN
	UPDATE book_examples SET decommissioned = TRUE WHERE book_ex_id = bk_ex_id;
	IF NOT need_to_ret THEN
		UPDATE book_ex_history SET real_ret_date = CAST(NOW() AS DATE) 
		WHERE book_ex_id = bk_ex_id AND real_ret_date = NULL;
	END IF;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE book_dereg(_book_id INT, need_to_ret BOOL)
AS $$ BEGIN
	UPDATE book SET decommissioned = TRUE WHERE book_id = _book_id;
	UPDATE book_examples SET decommissioned = TRUE WHERE book_id = _book_id;
	IF NOT need_to_ret THEN
		UPDATE book_ex_history SET real_ret_date = CAST(NOW() AS DATE) 
		WHERE (real_ret_date = NULL) AND book_ex_id IN (SELECT book_ex_id FROM book_examples WHERE book_id = _book_id);
	END IF;
END; $$ LANGUAGE plpgsql;
--- DEREGISTER -----------------------------------------------------------------------------------------------------
--- ----------------------------------------------------------------------------------------------------------------












----



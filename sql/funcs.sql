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

--- END : AUTHORS -------------------------------------------------------------------------------------------------


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
		INSERT INTO books(title, about, publisher_id, pub_year, ISBN, total_amount, spare_amount) VALUES 
		(LOWER(title), about, publ_id, TO_DATE(pub_year::text, 'YYYY'), ISBN, amount, amount) RETURNING book_id
	)
	SELECT * FROM bk INTO ret_book_id;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_book(title TEXT, about TEXT, publ_name TEXT, pub_year INT, ISBN VARCHAR(25), amount INT, OUT ret_book_id INTEGER)
AS $$
BEGIN
	WITH bk AS (
		INSERT INTO books(title, about, publisher_id, pub_year, ISBN, total_amount, spare_amount) VALUES 
		(LOWER(title), about, get_publisher_id(publ_name), TO_DATE(pub_year::text, 'YYYY'), ISBN, amount, amount) RETURNING book_id
	)
	SELECT * FROM bk INTO ret_book_id;
END;
$$
LANGUAGE plpgsql;

--- END : BOOKS -------------------------------------------------------------------------------------------------


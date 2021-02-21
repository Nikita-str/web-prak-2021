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

CREATE OR REPLACE PROCEDURE add_author(f_name VARCHAR(40), s_name VARCHAR(40), patr VARCHAR(40))
AS $$
BEGIN
	INSERT INTO authors(second_name, first_name, patronymic) VALUES (LOWER(s_name), LOWER(f_name), LOWER(patr));
END;
$$
LANGUAGE plpgsql;

--- END : AUTHORS -------------------------------------------------------------------------------------------------



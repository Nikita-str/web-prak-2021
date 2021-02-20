
/*
DROP TABLE IF EXISTS author_book;
DROP TABLE IF EXISTS book_ex_history;

DROP TABLE IF EXISTS book_examples;

DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS publishers;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS readers;
*/

CREATE TABLE publishers (
	p_id SERIAL PRIMARY KEY,
	p_name TEXT 
);


CREATE TABLE authors (
	author_id SERIAL PRIMARY KEY,
	second_name VARCHAR(40) NOT NULL,
	first_name VARCHAR(40) NOT NULL,
	patronymic VARCHAR(40),
	CONSTRAINT len_of_name_more_than_zero CHECK (0 < LENGTH(first_name) AND 0 < LENGTH(second_name)),
	CONSTRAINT len_of_patr_more_than_zero CHECK ((patronymic is NULL) OR (0 < LENGTH(patronymic)))
);


CREATE TABLE books (
	book_id SERIAL PRIMARY KEY,
	title TEXT NOT NULL,
	publisher_id INTEGER REFERENCES publishers(p_id),--can be null 
	pub_year DATE, -- can be null if we don't know when it was published
	ISBN VARCHAR(25), --can be null, old book or small circulation
	total_amount INTEGER  DEFAULT(0) NOT NULL,
	spare_amount INTEGER  DEFAULT(0) NOT NULL,
	decommissioned BOOL DEFAULT(FALSE),
	CONSTRAINT spare_LOE_total CHECK(spare_amount <= total_amount), --Less Or Equeal = LOE 
	CONSTRAINT amount_MOE_zero CHECK((total_amount >= 0) AND (spare_amount >= 0)) -- More Or Equal = MOE 
);

CREATE TABLE author_book (
	book_id INTEGER REFERENCES books(book_id) NOT NULL,
	author_id INTEGER REFERENCES authors(author_id) NOT NULL,
	CONSTRAINT already_added UNIQUE (book_id, author_id)
);

CREATE TABLE book_examples (
	book_ex_id SERIAL PRIMARY KEY,
	book_id INTEGER REFERENCES books(book_id) NOT NULL,
	spare BOOL DEFAULT(TRUE),
	decommissioned BOOL DEFAULT(FALSE)
);

CREATE TABLE readers (
	library_card_id SERIAL PRIMARY KEY,
	second_name VARCHAR(40) NOT NULL,
	first_name VARCHAR(40) NOT NULL,
	patronymic VARCHAR(40),
	address TEXT,
	phone_number VARCHAR(30),
	CONSTRAINT len_of_name_more_than_zero CHECK (0 < LENGTH(first_name) AND 0 < LENGTH(second_name)),
	CONSTRAINT len_of_patr_more_than_zero CHECK ((patronymic is NULL) OR (0 < LENGTH(patronymic)))
);

CREATE TABLE book_ex_history (
	book_ex_id INTEGER REFERENCES book_examples(book_ex_id) NOT NULL,
	lib_card_id INTEGER REFERENCES readers(library_card_id) NOT NULL,
	date_of_issue DATE NOT NULL,
	shedule_ret_date DATE, -- can be null if book was decommissioned 
	real_ret_date DATE,-- null if not yet ret 
	CONSTRAINT issue_LOE_shedule_ret_date CHECK((shedule_ret_date is NULL) OR (date_of_issue <= shedule_ret_date)),
	CONSTRAINT issue_LOE_real_ret_date CHECK((real_ret_date is NULL) OR (date_of_issue <= real_ret_date))
);





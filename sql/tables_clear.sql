/*
SET ROLE prak_user;
SELECT current_user;
*/

TRUNCATE readers CASCADE;
TRUNCATE book_ex_history CASCADE;
TRUNCATE publishers CASCADE;
TRUNCATE authors CASCADE;
TRUNCATE author_book CASCADE;
TRUNCATE book_examples CASCADE;
TRUNCATE books CASCADE;

ALTER SEQUENCE publishers_p_id_seq RESTART WITH 1;
ALTER SEQUENCE readers_library_card_id_seq RESTART WITH 1;
ALTER SEQUENCE authors_author_id_seq RESTART WITH 1;
ALTER SEQUENCE books_book_id_seq RESTART WITH 1;
ALTER SEQUENCE book_examples_book_ex_id_seq RESTART WITH 1;
ALTER SEQUENCE book_ex_history_beh_id_seq RESTART WITH 1;

import org.hibernate.Session;
javax.persistence.ParameterMode;
javax.persistence.StoredProcedureQuery;

public class Temp {
    public FindPublisher(String publ_name, Boolean complete_match) {

    public GetPublisherId(String publ_name, Integer ret_p_id) {

    public FindAuthor(String f_name, String s_name, String patr, Boolean complete_match) {

    public FindAuthor(String s0, String s1, Boolean complete_match) {

    public FindAuthor(String surname, Boolean complete_match) {

    public AddAuthor(String f_name, String s_name, String patr, Integer ret_author_id) {

    public AddAuthorToBook(Integer author_id, Integer book_id) {

    public GetAuthorsOfBook(Integer _book_id) {

    public GetAuthorId(String f_name, String s_name, String patr, Integer ret_author_id) {

    public AddBookEx(Integer bk_id, Integer amount) {

    public AddBook(String title, Integer ret_book_id) {

    public AddBook(String title, String about, Integer publ_id, Integer pub_year, String ISBN, Integer amount, Integer ret_book_id) {

    public AddBook(String title, String about, String publ_name, Integer pub_year, String ISBN, Integer amount, Integer ret_book_id) {

    public AddBook(String title, String about, Integer pub_year, String ISBN, Integer amount, Integer ret_book_id) {

    public AddReader(String f_name, String s_name, String patr) {

    public TheSameBook(Integer ex_id_1, Integer ex_id_2) {

    public BookAlreadyTaked(Integer bk_ex_id) {

    public BookIsDereg(Integer bk_ex_id) {

    public ReaderHasBookEx(Integer bk_ex_id, Integer _lib_card_id) {

    public BookTake(Integer book_ex_id, Integer lib_card_id, Integer day_for_ret) {

    public BookRet(Integer bk_ex_id) {

    public GetExBookHistory(Integer bk_ex_id) {

    public GetReaderHistory(Integer _lib_card_id) {

    public GetReaderCurBook(Integer _lib_card_id) {

    public GetReaderOverdueBook(Integer _lib_card_id, Boolean only_cur) {

    public ReaderCanPassLibCard(Integer _lib_card_id) {

    public BookExDereg(Integer bk_ex_id, Boolean need_to_ret) {

    public BookDereg(Integer _book_id, Boolean need_to_ret) {

}

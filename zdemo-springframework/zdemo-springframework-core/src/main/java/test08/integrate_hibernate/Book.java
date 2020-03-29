package test08.integrate_hibernate;

public class Book {
	private int id;
	private String isbn;
	private String bookName;
	private int price;
	
	public Book() {
		super();
	}
	public Book(String isbn, String bookName, int price) {
		super();
		this.isbn = isbn;
		this.bookName = bookName;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", isbn=" + isbn + ", bookName=" + bookName
				+ ", price=" + price + "]";
	}
}

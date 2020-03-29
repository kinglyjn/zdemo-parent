package test08.integrate_hibernate;

public class BookStock {
	private int id;
	private String isbn;
	private int stock;
	
	public BookStock() {
		super();
	}
	public BookStock(String isbn, int stock) {
		super();
		this.isbn = isbn;
		this.stock = stock;
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
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		return "BookStock [id=" + id + ", isbn=" + isbn + ", stock=" + stock
				+ "]";
	}
}

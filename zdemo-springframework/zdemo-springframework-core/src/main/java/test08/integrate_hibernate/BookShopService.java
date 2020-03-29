package test08.integrate_hibernate;


public interface BookShopService {
	/**
	 * 用户在书店买书接口
	 * @param username
	 * @param isbn
	 */
	void sellBook(String username, String isbn);
}

package ro.utcluj.model.library;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Library extends Observable {

	private static Library    instance;
	private        List<Book> books;

	private Library() {
		books = new LinkedList<>();
		readBooksFromFile();
	}

	public static Library getInstance() {
		if (instance == null)
			instance = new Library();
		return instance;
	}

	private void readBooksFromFile() {
		try {
			File fXmlFile = new File("Books.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Book");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String title = eElement.getElementsByTagName("title").item(0).getTextContent();
					String author = eElement.getElementsByTagName("author").item(0).getTextContent();
					int year = Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
					String genre = eElement.getElementsByTagName("genre").item(0).getTextContent();
					int quantity = Integer.parseInt(eElement.getElementsByTagName("quantity").item(0).getTextContent());
					double price = Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent());
					Book book = new Book(title, author, genre, year, quantity, price);
					books.add(book);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void persist() {
		try {
			File fXmlFile = new File("Books.xml");
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
			doc.getDocumentElement().normalize();

			Element rootElement = doc.getDocumentElement();
			NodeList nList = doc.getElementsByTagName("Book");
			int temp;
			for (temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					Book book = books.get(temp);
					eElement.getElementsByTagName("title").item(0).setTextContent(book.getTitle());
					eElement.getElementsByTagName("author").item(0).setTextContent(book.getAuthor());
					eElement.getElementsByTagName("year").item(0).setTextContent(book.getYear() + "");
					eElement.getElementsByTagName("genre").item(0).setTextContent(book.getGenre());
					eElement.getElementsByTagName("quantity").item(0).setTextContent(book.getQuantity() + "");
					eElement.getElementsByTagName("price").item(0).setTextContent(book.getPrice() + "");
				}
			}

			if (books.size() > nList.getLength()) {
				for (; temp < books.size(); temp++) {
					Book book = books.get(temp);

					Element bookElement = doc.createElement("Book");
					rootElement.appendChild(bookElement);

					Element title = doc.createElement("title");
					title.appendChild(doc.createTextNode(book.getTitle()));
					bookElement.appendChild(title);

					Element auth = doc.createElement("author");
					auth.appendChild(doc.createTextNode(book.getAuthor()));
					bookElement.appendChild(auth);

					Element year = doc.createElement("year");
					year.appendChild(doc.createTextNode(book.getYear() + ""));
					bookElement.appendChild(year);

					Element genre = doc.createElement("genre");
					genre.appendChild(doc.createTextNode(book.getGenre()));
					bookElement.appendChild(genre);

					Element quantity = doc.createElement("quantity");
					quantity.appendChild(doc.createTextNode(book.getQuantity() + ""));
					bookElement.appendChild(quantity);

					Element price = doc.createElement("price");
					price.appendChild(doc.createTextNode(book.getPrice() + ""));
					bookElement.appendChild(price);

				}

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fXmlFile);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Library [books=" + books + "]";
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
		setChanged();
		notifyObservers();
		persist();
	}

	public Object[][] toObjectMatrix() {
		Object[][] result = new Object[books.size()][6];
		int index = 0;
		for (Book i : books) {
			result[index][0] = i.getTitle();
			result[index][1] = i.getAuthor();
			result[index][2] = i.getGenre();
			result[index][3] = i.getYear();
			result[index][4] = i.getPrice();
			result[index][5] = i.getQuantity();

			index++;
		}

		return result;
	}

	public Object[][] getBooksByAuthor(String author) {
		Object[][] result = new Object[books.size()][6];
		int index = 0;
		for (Book i : books)
			if (i.getAuthor().toLowerCase().contains(author.toLowerCase())) {
				result[index][0] = i.getTitle();
				result[index][1] = i.getAuthor();
				result[index][2] = i.getGenre();
				result[index][3] = i.getYear();
				result[index][4] = i.getPrice();
				result[index][5] = i.getQuantity();

				index++;
			}

		return result;
	}

	public Object[][] getBooksByTitle(String title) {
		Object[][] result = new Object[books.size()][6];
		int index = 0;
		for (Book i : books)
			if (i.getTitle().toLowerCase().contains(title.toLowerCase())) {
				result[index][0] = i.getTitle();
				result[index][1] = i.getAuthor();
				result[index][2] = i.getGenre();
				result[index][3] = i.getYear();
				result[index][4] = i.getPrice();
				result[index][5] = i.getQuantity();

				index++;
			}

		return result;
	}

	public Object[][] getBooksByYear(int year) {
		Object[][] result = new Object[books.size()][6];
		int index = 0;
		for (Book i : books)
			if (i.getYear() == year) {
				result[index][0] = i.getTitle();
				result[index][1] = i.getAuthor();
				result[index][2] = i.getGenre();
				result[index][3] = i.getYear();
				result[index][4] = i.getPrice();
				result[index][5] = i.getQuantity();

				index++;
			}

		return result;
	}

	public boolean sell(String title, String author, int year, int quantity) {
		for (Book i : books) {
			if (i.getAuthor().equalsIgnoreCase(author) && i.getTitle().equalsIgnoreCase(title) && i.getYear() == year) {
				int stock = i.getQuantity();
				if (stock >= quantity) {
					i.setQuantity(stock - quantity);
					persist();
					setChanged();
					notifyObservers();
					return true;
				}

				break;
			}
		}

		return false;
	}

	public void addBook(Book book) {
		books.add(book);
		setChanged();
		notifyObservers();
		createBook(book);
	}

	private void createBook(Book book) {
		try {
			File fXmlFile = new File("Books.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Book");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String title = eElement.getElementsByTagName("title").item(0).getTextContent();
					String author = eElement.getElementsByTagName("author").item(0).getTextContent();
					int year = Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
					String genre = eElement.getElementsByTagName("genre").item(0).getTextContent();
					int quantity = Integer.parseInt(eElement.getElementsByTagName("quantity").item(0).getTextContent());
					double price = Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent());
					Book book2 = new Book(title, author, genre, year, quantity, price);
					if (book2.equals(book))
						return;
				}
			}

			Element rootElement = doc.getDocumentElement();

			Element bookElement = doc.createElement("Book");
			rootElement.appendChild(bookElement);

			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(book.getTitle()));
			bookElement.appendChild(title);

			Element auth = doc.createElement("author");
			auth.appendChild(doc.createTextNode(book.getAuthor()));
			bookElement.appendChild(auth);

			Element year = doc.createElement("year");
			year.appendChild(doc.createTextNode(book.getYear() + ""));
			bookElement.appendChild(year);

			Element genre = doc.createElement("genre");
			genre.appendChild(doc.createTextNode(book.getGenre()));
			bookElement.appendChild(genre);

			Element quantity = doc.createElement("quantity");
			quantity.appendChild(doc.createTextNode(book.getQuantity() + ""));
			bookElement.appendChild(quantity);

			Element price = doc.createElement("price");
			price.appendChild(doc.createTextNode(book.getPrice() + ""));
			bookElement.appendChild(price);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fXmlFile);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeBook(Book book) {
		books.remove(book);
		setChanged();
		notifyObservers();
		deleteBook(book);
	}

	private void deleteBook(Book book) {
		try {
			File fXmlFile = new File("Books.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Book");
			Element rootElement = doc.getDocumentElement();

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String title = eElement.getElementsByTagName("title").item(0).getTextContent();
					String author = eElement.getElementsByTagName("author").item(0).getTextContent();
					int year = Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
					/*
					 * String genre =
					 * eElement.getElementsByTagName("genre").item
					 * (0).getTextContent(); int quantity =
					 * Integer.parseInt(eElement
					 * .getElementsByTagName("quantity")
					 * .item(0).getTextContent()); double price =
					 * Double.parseDouble
					 * (eElement.getElementsByTagName("price").
					 * item(0).getTextContent());
					 */
					if (title.equalsIgnoreCase(book.getTitle()) && author.equalsIgnoreCase(book.getAuthor())
							&& year == book.getYear()) {
						rootElement.removeChild(nNode);
					}
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fXmlFile);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Book findBook(String title, String author, int year) {
		Book e = null;
		for (Book book : books) {
			if (title.equalsIgnoreCase(book.getTitle()) && author.equalsIgnoreCase(book.getAuthor())
					&& year == book.getYear()) {
				e = book;
				break;
			}
		}

		return e;
	}

}

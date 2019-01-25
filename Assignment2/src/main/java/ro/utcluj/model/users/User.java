package ro.utcluj.model.users;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public abstract class User {
	private String username;
	private String password;

	private Type type;


	User(String username, String password, Type type) {
		super();
		this.username = username;
		this.password = password;

		this.type = type;

	}

	public static User login(String username, String password) {
		try {
			File fXmlFile = new File("Users.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("User");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String type = eElement.getAttribute("type");
					String nodeUsername = eElement.getElementsByTagName("Username").item(0).getTextContent();
					String nodePassword = eElement.getElementsByTagName("Password").item(0).getTextContent();

					if (username.equals(nodeUsername) && password.equals(nodePassword)) {
						if (type.equalsIgnoreCase("admin"))
							return new Admin(username, password);
						else
							return new Employee(nodeUsername, nodePassword);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {
		ADMIN, EMPLOYEE
	}
}

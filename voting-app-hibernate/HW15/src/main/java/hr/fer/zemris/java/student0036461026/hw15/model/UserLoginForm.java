package hr.fer.zemris.java.student0036461026.hw15.model;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAOException;
import hr.fer.zemris.java.student0036461026.hw15.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw15.utility.Utility;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for modeling data supplied through user login form.
 * @author Tomislav
 *
 */

public class UserLoginForm {

	/**
	 * Users nickname.
	 */
	private String nick;
	/**
	 * Users password.
	 */
	private String password;

	/**
	 * Map containing error descriptions found in blog entry form.
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Default constructor.
	 */
	public UserLoginForm() {
	}

	/**
	 * Method returns error description found in blog entry form.
	 * @param name Error name.
	 * @return Error description.
	 */
	public String getError(String name) {
		return errors.get(name);
	}

	/**
	 * Method checks if blog entry form has errors.
	 * @return True or false value depending if errors are found.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Method checks if error map contains particular error.
	 * @param name Error name.
	 * @return True or false value depending if map contains error.
	 */
	public boolean containsError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Method fills user login class parameters from data in http request.
	 * @param req Http request.
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
	}

	/**
	 * Method validates user login form for errors.
	 */
	public void validate() {
		errors.clear();
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Username is required!");
		}
		
		if(this.password.isEmpty()) {
			errors.put("password", "Password is required!");
		}
		
		if (hasErrors()) {
			return;
		}
		
		BlogUser blogUser = null;
		try {
			blogUser = DAOProvider.getDAO().getBlogUser(nick);
		} catch (DAOException ex) {
			ex.printStackTrace();
		}		
		if (blogUser == null) {
			errors.put("nick", "Incorrect username!");
			return;
		}
		
		String hashPassword = Utility.computeHash(password, "SHA-1");
		
		if (!hashPassword.equals(blogUser.getPasswordHash())) {
			errors.put("password", "Incorrect password!");
		}
	}

	/**
	 * Method trims given string or sets him to empty 
	 * string is he is null.
	 * @param s String.
	 * @return Processed string.
	 */
	private String prepare(String s) {
		if(s==null) {
			return "";
		}
		return s.trim();
	}

	/**
	 * Method returns users nickname.
	 * @return Users nickname.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Method sets users nickname.
	 * @param nick Users nickname.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Method returns user login form provided password.
	 * @return User login form provided password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *  Method sets user login form password.
	 * @param password User login form password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

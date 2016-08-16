package hr.fer.zemris.java.student0036461026.hw15.model;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAOException;
import hr.fer.zemris.java.student0036461026.hw15.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw15.utility.Utility;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for modeling data supplied through user registration form.
 * @author Tomislav
 *
 */

public class UserRegisterForm {
	
	/**
	 * Users first name.
	 */
	private String firstName;
	/**
	 * Users last name.
	 */
	private String lastName;
	/**
	 * Users nickname.
	 */
	private String nick;
	/**
	 * Users email
	 */
	private String email;
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
	public UserRegisterForm() {
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
	 * Method fills user registration class parameters from data in http request.
	 * @param req Http request.
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.email = prepare(req.getParameter("email"));
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
	}
	
	/**
	 * Method validates user registration form for errors.
	 */
	public void validate() {
		errors.clear();
		
		if(this.firstName.isEmpty()) {
			errors.put("firstName", "First name is required!");
		}
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is required!");
		}
		
		if(this.email.isEmpty()) {
			errors.put("email", "Email is required!");
		} 
		else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "Invalid email format.");
			}
		}
		
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
		
		if (blogUser != null) {
			errors.put("nick", "Username already exist!!");
			return;
		}
	}
	
	/**
	 * Method fills user registration class parameters from blog user object.
	 * @param blogUser Blog user.
	 */
	public void fillBlogUser(BlogUser blogUser) {		
		blogUser.setFirstName(firstName);
		blogUser.setLastName(lastName);
		blogUser.setEmail(email);
		blogUser.setNick(nick);
		blogUser.setPasswordHash(Utility.computeHash(password, "SHA-1"));
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
	 * Method returns users first name.
	 * @return Users first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Method sets users first name.
	 * @param firstName Users first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Method returns users last name.
	 * @return Users last name.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 *  Method sets users last name.
	 * @param lastName Users last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * Method returns users email.
	 * @return Users email.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Method sets users email.
	 * @param email Users email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Method returns users password.
	 * @return Users password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Method sets users password.
	 * @param password Users password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

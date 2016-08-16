package hr.fer.zemris.java.student0036461026.hw15.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class models blog entry given through blog entry form when blog 
 * entry is created or updated.
 * @author Tomislav
 *
 */
public class BlogEntryForm {
	
	/**
	 * Blog entry id.
	 */
	private Long id;
	/**
	 * Blog entry title.
	 */
	private String title;
	/**
	 * Blog entry text.
	 */
	private String text;
	
	/**
	 * Map containing error descriptions found in blog entry form.
	 */
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * Default constructor.
	 */
	public BlogEntryForm() {
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
	 * Method fills blog entry form from blog entry object
	 * @param entry Blog entry.
	 */
	public void fillFromBlogEntry(BlogEntry entry) {
		this.id = entry.getId();
		this.title = entry.getTitle();
		this.text = entry.getText();
	}

	/**
	 * Method fills blog entry form from data in http request.
	 * @param req Http request.
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
	}
	
	/**
	 * Method validates blog entry form for errors.
	 */
	public void validate() {
		errors.clear();
		
		if(this.title.isEmpty()) {
			errors.put("title", "Title is required!");
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
	 * Method returns blog entry id.
	 * @return Blog entry id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Method sets blog entry id.
	 * @param id Blog entry id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Method returns blog entry title.
	 * @return Blog entry title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method sets blog entry title.
	 * @param title Blog entry title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 *  Method returns blog entry text.
	 * @return Blog entry text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Method sets blog entry text.
	 * @param text Blog entry text.
	 */
	public void setText(String text) {
		this.text = text;
	}
}

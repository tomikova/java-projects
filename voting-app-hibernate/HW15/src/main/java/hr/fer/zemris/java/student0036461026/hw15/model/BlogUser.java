package hr.fer.zemris.java.student0036461026.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class models blog user.
 * @author Tomislav
 *
 */

@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {
	
	/**
	 * User id.
	 */
	private Long id;
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
	 * Users email.
	 */
	private String email;
	/**
	 * Hash of users password.
	 */
	private String passwordHash;
	/**
	 * List of users blog entries.
	 */
	private List<BlogEntry> blogEntries = new ArrayList<>();
	
	/**
	 * Method returns user id.
	 * @return User id.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Methos sets user id.
	 * @param id User id.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Method returns list of user blog entries.
	 * @return List of user blog entries.
	 */
	@OneToMany(mappedBy="blogUser", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}
	
	/**
	 * Method sets user blog entries.
	 * @param blogEntries Blog entries.
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	
	/**
	 * Method returns users first name.
	 * @return Users first name.
	 */
	@Column(length=50,nullable=false)
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
	@Column(length=50,nullable=false)
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
	@Column(length=50,nullable=false, unique=true)
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
	@Column(length=100,nullable=false)
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
	 * Method returns hash of users password.
	 * @return Hash of users password.
	 */
	@Column(length=100,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Method sets hash of users password.
	 * @param passwordHash Hash of users password.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Method calculates hash code of users id.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Method for users comparison.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}

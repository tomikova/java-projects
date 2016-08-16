package hr.fer.zemris.java.student0036461026.hw15.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class models blog comment.
 * @author Tomislav
 *
 */

@Entity
@Table(name="blog_comments")
@Cacheable(true)
public class BlogComment {

	/**
	 * Blog comment id.
	 */
	private Long id;
	/**
	 * Blog entry of comment.
	 */
	private BlogEntry blogEntry;
	/**
	 * Commentator email.
	 */
	private String email;
	/**
	 * Comment message.
	 */
	private String message;
	/**
	 * Time when comment is posted.
	 */
	private Date postedOn;

	/**
	 * Method return comment id.
	 * @return Comment id.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Method sets comment id.
	 * @param id Comment id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Method return blog entry of comment.
	 * @return Blog entry of comment.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Methos sets blog entry of comment.
	 * @param blogEntry Blog entry.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Method returns commentator email.
	 * @return Commentator email.
	 */
	@Column(length=100,nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Method sets commentator email.
	 * @param email Commentator email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Method returns comment message.
	 * @return Comment message.
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Method sets comment message.
	 * @param message Comment message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Method returns time when comment was posted.
	 * @return Time when comment was posted.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Method sets time when comment was posted.
	 * @param postedOn Time when comment was posted.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * Method calculates hash code of comment id.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Method for comment comparison.
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
		BlogComment other = (BlogComment) obj;
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

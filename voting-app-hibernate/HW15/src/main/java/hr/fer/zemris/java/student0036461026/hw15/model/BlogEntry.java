package hr.fer.zemris.java.student0036461026.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class models blog entry.
 * @author Tomislav
 *
 */

@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Blog entry id.
	 */
	private Long id;
	/**
	 * Blog entry comments.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Time when blog entry was created.
	 */
	private Date createdAt;
	/**
	 * Time when blog entry was last modified.
	 */
	private Date lastModifiedAt;
	/**
	 * Blog entry title.
	 */
	private String title;
	/**
	 * Blog entry text.
	 */
	private String text;
	/**
	 * Blog entry owner.
	 */
	private BlogUser blogUser;

	/**
	 * Method returns blog entry id.
	 * @return Blog entry id.
	 */
	@Id @GeneratedValue
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
	 * Method returns blog entry owner.
	 * @return Blog entry owner.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getBlogUser() {
		return blogUser;
	}

	/**
	 * Method sets blog entry owner.
	 * @param blogUser Blog entry owner.
	 */
	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
	}

	/**
	 * Method returns blog entry comments.
	 * @return Blog entry comments.
	 */
	@OneToMany(mappedBy="blogEntry", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Method sets blog entry comments.
	 * @param comments Blog entry comments.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Method returns time when blog entry was created.
	 * @return Time when blog entry was created.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Method sets time when blog entry was created.
	 * @param createdAt Time when blog entry was created.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Method returns time when blog entry was last modified.
	 * @return Time when blog entry was last modified.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Method sets time when blog entry was last modified.
	 * @param lastModifiedAt Time when blog entry was last modified.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Method returns blog entry title.
	 * @return Blog entry title.
	 */
	@Column(nullable=false, length=200)
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
	@Column(nullable=false, length=4096)
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

	/**
	 * Method calculates hash code of blog entry id.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Method for blog entry comparison.
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
		BlogEntry other = (BlogEntry) obj;
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

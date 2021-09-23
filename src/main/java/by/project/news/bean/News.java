package by.project.news.bean;

import java.io.Serializable;
import java.util.Objects;

public class News implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	private String brief;
	private String body;
	private String style;

	News() {
	}

	News(NewsBuilder newsBuilder) {
		this.id = newsBuilder.id;
		this.title = newsBuilder.title;
		this.brief = newsBuilder.brief;
		this.body = newsBuilder.body;
		this.style = newsBuilder.style;

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, style, body, brief, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		return Objects.equals(id, other.id) && Objects.equals(style, other.style) && Objects.equals(body, other.body) && Objects.equals(brief, other.brief)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return getClass().getName() + " [id=" + id + ", title=" + title + ", brief=" + brief + ", body=" + body + ", style=" + style + "]";
	}

	//Builder
	
	public static class NewsBuilder {

		// optional
		private String id;
		private String title;
		private String brief;
		private String body;
		private String style;

		public NewsBuilder() {
		}

		public NewsBuilder setId(String id) {

			this.id = id;
			return this;
		}
		
		public NewsBuilder setTitle(String title) {

			this.title = title;
			return this;
		}

		public NewsBuilder setBrief(String brief) {

			this.brief = brief;
			return this;
		}

		public NewsBuilder setBody(String body) {

			this.body = body;
			return this;
		}
		
		public NewsBuilder setStyle(String style) {

			this.style = style;
			return this;
		}

		public News build() {

			return new News(this);
		}

	}

}

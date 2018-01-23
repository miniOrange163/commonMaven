package priv.linjb.common.parse.dom4j;

public class Info {

	private String id;
	private String title;
	private String content;
	private String author;
	private String pubtime;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	@Override
	public String toString() {
		return "Info [author=" + author + ", content=" + content + ", id=" + id
				+ ", pubtime=" + pubtime + ", title=" + title + "]";
	}

	
	
}

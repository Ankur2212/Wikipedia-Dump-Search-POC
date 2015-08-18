import java.util.ArrayList;
import java.util.List;


public class Revision {
	private String id = null;
	private String timestamp = null;
	private List<Contributor> contributor = new ArrayList<Contributor>();
	private String minor = null;
	private String comment = null;
	private String text = null;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the contributor
	 */
	public List<Contributor> getContributor() {
		return contributor;
	}
	/**
	 * @param contributor the contributor to set
	 */
	public void setContributor(List<Contributor> contributor) {
		this.contributor = contributor;
	}
	/**
	 * @return the minor
	 */
	public String getMinor() {
		return minor;
	}
	/**
	 * @param minor the minor to set
	 */
	public void setMinor(String minor) {
		this.minor = minor;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Revision [id=" + id + ", timestamp=" + timestamp
				+ ", contributor=" + contributor + ", minor=" + minor
				+ ", comment=" + comment + ", text=" + text + "]";
	}
	
}

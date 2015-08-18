import java.util.HashMap;
import java.util.Map;


public class Page {
	private String id = null;
	private String title = null;
	private Map<String,Revision> revision = new HashMap<String,Revision>();
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the revision
	 */
	public Map<String,Revision> getRevision() {
		return revision;
	}
	/**
	 * @param revision the revision to set
	 */
	public void setRevision(Map<String,Revision> revision) {
		this.revision = revision;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Page [id=" + id + ", title=" + title + ", revision=" + revision
				+ "]";
	}
		
}

package in.com.rays.proj4.bean;

/**
 * @author Rinku
 *
 */
public class CourseBean extends BaseBean {

	/**
	 * name of course
	 */
	private String courseName;

	/**
	 * name of description
	 */
	private String description;

	/**
	 * name of duration
	 */
	private String duration;

	/**
	 * accessor
	 */
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getKey() {
		
		return id + "";
	}

	public String getValue() {
		
		return courseName+"";
	}

}

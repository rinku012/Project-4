package in.com.rays.proj4.bean;

import java.util.Date;

//TimeTable JavaBean encapsulates TimeTable attributes

/**
 * @author Rinku
 *
 */
public class TimeTableBean extends BaseBean {


	/** CourseID of TimeTable. */
	private long courseId;

	/** CourseName of TimeTable. */
	private String courseName;

	/** SubjectID of TimeTable. */
	private long subjectId;

	/** SubjectName of TimeTable. */
	private String subjectName;


	/** Semester Wise of TimeTable. */
	private String semester;

	/** ExamDate of TimeTable. */
	private Date examDate;

	/** ExamTime of TimeTable. */
	private String examTime;
	


	/**
	 * Getter and Setter of TimeTable.
	 *
	 * @return the subject id
	 */
	
	public long getSubjectId() {
		return subjectId;
	}

	/**
	 * Sets the subject id.
	 *
	 * @param subjectId the new subject id
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Gets the subject name.
	 *
	 * @return the subject name
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets the subject name.
	 *
	 * @param subjectName the new subject name
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Gets the course id.
	 *
	 * @return the course id
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course id.
	 *
	 * @param courseId the new course id
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Gets the course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name.
	 *
	 * @param courseName the new course name
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the semester.
	 *
	 * @return the semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * Sets the semester.
	 *
	 * @param semester the new semester
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * Gets the exam date.
	 *
	 * @return the exam date
	 */
	public Date getExamDate() {
		return examDate;
	}

	/**
	 * Sets the exam date.
	 *
	 * @param examDate the new exam date
	 */
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	/**
	 * Gets the exam time.
	 *
	 * @return the exam time
	 */
	public String getExamTime() {
		return examTime;
	}

	/**
	 * Sets the exam time.
	 *
	 * @param examTime the new exam time
	 */
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

    /* (non-Javadoc)
     * @see in.co.rays.ors.bean.DropdownListBean#getKey()
     */
    public String getKey() {
        return id + "";
    }

   
    /* (non-Javadoc)
     * @see in.co.rays.ors.bean.DropdownListBean#getValue()
     */
    public String getValue() {
        return subjectName;
    } 
}

package in.com.rays.proj4.bean;

import java.util.Date;

//faculty JavaBean encapsulates faculty attributes;

/**
 * @author Rinku
 *
 */
public class FacultyBean extends BaseBean {

	/** First Name of Faculty. */
	private String firstName;

    /** Last Name of Faculty. */
	private String lastName;

    /** Gender of Faculty. */
	private String Gender;

    /** Login ID of Faculty. */
	private String emailId;
	
	

    /** Mobile No of Faculty. */
	private String mobileNo;

    /** CollegeID of Faculty. */
	private long collegeId;

    /** College Name of Faculty. */
	private String collegeName;

    /** CourseID of Faculty. */
	private long courseId;

    /** Course Name of Faculty. */
	private String courseName;

	/** dob of Faculty. */
	private Date dob;
	
  

	/** SubjectID of Faculty. */
	private long subjectId;

    /** Subject Name of Faculty. */
	private String subjectName;


    /**
     * Getter and Setter of Faculty.
     *
     * @return the first name
     */
	   
   
    public String getFirstName() {
		return firstName;
	}


	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return Gender;
	}


	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		Gender = gender;
	}


	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmailId() {
		return emailId;
	}


	/**
	 * Sets the email id.
	 *
	 * @param emailId the new email id
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}


	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	/**
	 * Gets the college id.
	 *
	 * @return the college id
	 */
	public long getCollegeId() {
		return collegeId;
	}


	/**
	 * Sets the college id.
	 *
	 * @param collegeId the new college id
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}


	/**
	 * Gets the college name.
	 *
	 * @return the college name
	 */
	public String getCollegeName() {
		return collegeName;
	}


	/**
	 * Sets the college name.
	 *
	 * @param collegeName the new college name
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
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
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}


	/**
	 * Sets the dob.
	 *
	 * @param dob the new dob
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}


	/**
	 * Gets the subject id.
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
        return firstName +""+ lastName;
    }
}
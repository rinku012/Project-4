package in.com.rays.proj4.bean;

import java.io.Serializable;
import java.sql.Timestamp;
 
      //Parent class of all Beans in application.

	
	/**
	 * @author Rinku
	 *
	 */
	public abstract class BaseBean implements Serializable, DropdownListBean,
    Comparable<BaseBean> {
		/** Non Business primary key. */
		 protected long id;
		 /** Contains USER ID who created this database record. */
		 protected String createdBy;

	     /** Contains USER ID who modified this database record. */
		 protected String modifiedBy;
		 /** Contains Created Timestamp of database record. */
		 protected Timestamp createdDatetime;
		 /** Contains Modified Timestamp of database record. */
		 protected Timestamp modifiedDatetime;
		
		 /**
			 * accessor.
			 *
			 * @return the id
			 */
		 public long getId() {
			return id;
		}
		 /**
			 * Sets the id.
			 *
			 * @param id the new id
			 */
		public void setId(long id) {
			this.id = id;
		}
		/**
		 * Gets the created by.
		 *
		 * @return the created by
		 */
		public String getCreatedBy() {
			return createdBy;
		}
		/**
		 * Sets the created by.
		 *
		 * @param createdBy the new created by
		 */
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public Timestamp getCreatedDatetime() {
			return createdDatetime;
		}
		public void setCreatedDatetime(Timestamp createdDatetime) {
			this.createdDatetime = createdDatetime;
		}
		public Timestamp getModifiedDatetime() {
			return modifiedDatetime;
		}
		public void setModifiedDatetime(Timestamp modifiedDatetime) {
			this.modifiedDatetime = modifiedDatetime;
		}

		
		  
		
		public int compareTo(BaseBean next) {
		        return getValue().compareTo(next.getValue());
		    }
		
}



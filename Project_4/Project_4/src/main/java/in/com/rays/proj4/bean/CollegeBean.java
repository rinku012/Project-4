package in.com.rays.proj4.bean;


//College JavaBean encapsulates College attributes

/**
 * @author Rinku
 *
 */
public class CollegeBean extends BaseBean {
		
	 private String name;
	 private String address;
	 private String state;
	 private String city;
	 private String phonoNo;
	 private String createdBy;
	 private String modifiedBy;
	 
	
	 public CollegeBean () {}
	 
	 public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhonoNo() {
		return phonoNo;
	}
	public void setPhonoNo(String phonoNo) {
		this.phonoNo = phonoNo;
	}
	 public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
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
        return name;
    }
	 
	
}

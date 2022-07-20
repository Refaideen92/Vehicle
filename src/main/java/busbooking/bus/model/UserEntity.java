package busbooking.bus.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	
	@Id
	@Column
	private String username;
	
	@Column
	private String password;
	
	@OneToOne(cascade=CascadeType.ALL ,fetch=FetchType.EAGER,orphanRemoval=true,targetEntity=UserDetailEntity.class)
	@JoinColumn(name="userDetail" ,referencedColumnName="userid")
	private UserDetailEntity userDetail;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDetailEntity getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetailEntity userDetail) {
		this.userDetail = userDetail;
	}
	
	
}

package checklist.com.server.BestCheckListEver.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.OneToMany;

@Entity
@Table (name = "users")
public class AppUser {

	private String name;
	private String password;
	private Role role; 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonManagedReference
	@OneToMany(mappedBy= "user", fetch = FetchType.EAGER)
	private List<Activity> activitiesList;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
	
	public List<Activity> getActivitiesList(){
		return this.activitiesList;
	}

	public void setActivitiesList(List<Activity> list){
		this.activitiesList = list; 
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}
}

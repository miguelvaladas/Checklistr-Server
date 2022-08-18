package checklist.com.server.BestCheckListEver.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table (name = "users")
public class User {

	private String name;
	private String password;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

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
}

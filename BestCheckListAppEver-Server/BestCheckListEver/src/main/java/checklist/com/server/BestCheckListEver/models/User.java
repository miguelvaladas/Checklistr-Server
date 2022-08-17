package checklist.com.server.BestCheckListEver.models;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table (name = "users")
public class User {

	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToMany(mappedBy= "user")
	private ArrayList<Activity> activitiesList;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
	
	public ArrayList<Activity> getActivitiesList(){
		return this.activitiesList;
	}

	public void setActivitiesList(ArrayList<Activity> list){
		this.activitiesList = list; 
	}
}

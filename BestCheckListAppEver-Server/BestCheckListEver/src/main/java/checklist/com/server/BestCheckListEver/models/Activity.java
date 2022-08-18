package checklist.com.server.BestCheckListEver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table ( name = "activities" )
public class Activity{

	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Integer id;

	@ManyToOne
	private User user;

	private String description;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}
	
	@Autowired
	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return this.user;
	}

}

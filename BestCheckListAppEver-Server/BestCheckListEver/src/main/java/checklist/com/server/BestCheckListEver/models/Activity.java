package checklist.com.server.BestCheckListEver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table ( name = "activities" )
public class Activity{

	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Integer id;

	@ManyToOne
	private User user;

	private void setId(Integer id){
		this.id = id;
	}

	private Integer getId(){
		return this.id;
	}

	private void setUser(User user){
		this.user = user;
	}

	private User getUser(){
		return this.user;
	}

}

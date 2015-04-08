
package models;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class User extends Model{
    
    @Id
	public String user_name;
	public String password;
	
	

	public User(String user_name, String password){

		this.user_name = user_name;
		this.password = password;
		

	}


}

package models;

import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class PassConfirm extends Model{

	@Id 
	public String oldPassword;
	public String newPassword;
	public String confirmPassword;
	
	public PassConfirm(String oldPassword, String newPassword, String confirmPassword){
	    
	    this.oldPassword = oldPassword;
	    this.newPassword = newPassword;
	    this.confirmPassword = confirmPassword;

	}
	
	
}
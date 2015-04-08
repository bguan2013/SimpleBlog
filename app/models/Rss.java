
package models;

import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class Rss extends Model{

	@Id
	public String rssTitle;

	public String rssAuthor;
	public String rssDescription;
	public String rssDate;


	public Rss(String rssTitle, String rssAuthor, String rssDescription, String rssDate){
		
		this.rssAuthor = rssAuthor;
		this.rssTitle = rssTitle;
		this.rssDescription = rssDescription;
		this.rssDate = rssDate;
	}

	

}
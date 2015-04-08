package models;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Blog extends Model{

	 
	

    @Id
	public String blog_title;
	public String blog_post;
	public String date;
	public String user_name;
	
	public Blog(String blog_title, String blog_post, String date, String user_name){

		this.user_name = user_name;
		this.blog_title = blog_title;
		this.blog_post = blog_post;
		this.date = date;
		
		
	}

	
}
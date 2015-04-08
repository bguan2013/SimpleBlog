package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

import java.sql.*;
import java.util.*;

import play.data.Form;

import play.db.ebean.Model;

import static play.libs.Json.*;

import java.io.*;
import java.net.URL;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.feed.synd.SyndEntry;

public class Application extends Controller{
    
    
    public static Result welcome(){
        
        return ok(welcome.render());
        
    }
    
    
    public static Result viewblog(){
        
        List<Blog> blogs = getBlogs();
        String username = session("user");
        
        return ok(viewblog.render(blogs,username));
    }
    
 
    public static Result writeblog(){
        
        String username = session("user");
        
        if(username != null){
            
            return ok(writeblog.render(username));
            
        }
        else{
            return redirect(routes.Application.viewblog());
        }
    }
 
    public static Result login(){
        
        User user = Form.form(User.class).bindFromRequest().get();
        
        if(user==null){
            return ok(login.render(null));
        }
        else if(user.user_name == null && user.password == null){
            
            return ok(login.render(null));
        }
        
        else if(user.user_name == null || user.password == null){
            return ok(login.render("Please enter in both fields!"));
        }
        else{
            
            Boolean pass = authenticateUser(user.user_name, user.password);
            
            if(pass){
                session("user",user.user_name);
                return redirect(routes.Application.viewblog());
            }
            else{
                return ok(login.render("Wrong username or password!"));
            }
            
        }
    }
    
    public static Result logout(){
        
        session().clear();
        return redirect(routes.Application.login());
    }
    
    public static Result register(){
        
        User user = Form.form(User.class).bindFromRequest().get();
        
        if(user==null){
            return ok(register.render(null));
        }
        else if(user.user_name == null && user.password == null){
            
            return ok(register.render(null));
        }
        else if(user.user_name == null || user.password == null){
            return ok(register.render("Please enter in both fields!"));
        }
        else{
            Boolean exist = checkIfUserExists(user.user_name);
            
            if(exist){
                return ok(register.render("Username already exists!"));
            }
            else{
                user.save();
                session("user",user.user_name);
                return redirect(routes.Application.viewblog());
            }
            
        }
        
    }
    
    public static Result rssnews(){
        
        Rss[] rss = getRSSNewsArrayWithSize(10);
        String username = session("user");
        return ok(rssnews.render(rss,username));
    }
    
    public static Result setting(){
        
        PassConfirm confirm = Form.form(PassConfirm.class).bindFromRequest().get();
        String username = session("user");
        
        if(username == "" || username == null){
            return redirect("routes.Application.viewblog()");
        }
        
        else if(authenticateUser(username, confirm.oldPassword)){
            
            if(confirm.newPassword == confirm.confirmPassword){
                return ok("<meta http-equiv=\"refresh\" content=\"3; url = @routes.Application.viewblog() \" />" +
                "<p>Password Changed<p>"); 
            }
            else{
                return ok(setting.render("Invalid New Password!"));
            }
            
        }
        else{
            return ok(setting.render(""));
        }
        
    }
    
    
    
    public static boolean checkIfUserExists(String username){
        
        User user = (User)new Model.Finder(String.class, User.class).byId(username);
        if(user == null){
            return false;
        }
        else{
            return true;
        }
    }
    
    public static boolean authenticateUser(String username, String password){
        
        User user = (User)new Model.Finder(String.class, User.class).byId(username);
        if(user == null){
            return false;
        }
        else if(!user.password.equals(password)){
            
            return false;
        }
        else{
            return true;
        }
        
    }
    
    public static Result saveblog(){
        
        Blog blog = Form.form(Blog.class).bindFromRequest().get();
        blog.save();
        return redirect(routes.Application.viewblog());
    }
    
    public static List<Blog> getBlogs(){
        
        List<Blog> blogs = new Model.Finder(String.class, Blog.class).all();
        return blogs;
        
    }
    
    @SuppressWarnings("unchecked")
        public static List<SyndEntry> getLastestNewsFromYahoo()throws IOException{

            URL url = new URL("http://rss.news.yahoo.com/rss/us");
            SyndFeedInput syndFeedInput = new SyndFeedInput();
            SyndFeed syndFeed = null;
            XmlReader xmlReader = new XmlReader(url);
            try{
                syndFeed = syndFeedInput.build(xmlReader);
                List<SyndEntry> rss = syndFeed.getEntries();
                return rss;
            }
            catch(IllegalArgumentException e){
                e.printStackTrace();
                return null;
            }
            catch(FeedException e){
                e.printStackTrace();
                return null;
            }

    }

        public static Rss[] getRSSNewsArrayWithSize(int size){

            Rss[] rssArray = new Rss[size];

            try{
                Iterator iterator = getLastestNewsFromYahoo().iterator();
                for(int i = 0; i < size; i++){
                    if(iterator.hasNext()){
                        SyndEntry syndEntry = (SyndEntry)iterator.next();
                        String author = syndEntry.getAuthor();
                        String title = syndEntry.getTitle();
                        String description = syndEntry.getDescription().getValue();
                        String date = syndEntry.getPublishedDate().toString();

                        //Temporary RSS ID
                        rssArray[i] = new Rss(title, author, description, date);
                    }
                }   
                return rssArray;
            }
            catch(IOException e){
                e.printStackTrace();
                return null;
            }   
        }
}

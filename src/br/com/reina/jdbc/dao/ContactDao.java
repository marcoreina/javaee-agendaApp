package br.com.reina.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.reina.jdbc.ConnectionFactory;
import br.com.reina.jdbc.model.Contact;

public class ContactDao {
	
	// connection with the database
    private Connection connection;
    
    public ContactDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void add(Contact contact) {
    	String sql = "insert into contacts " +
    				 "(name, email, address, birthday) " +
    				 "values (?,?,?,?)";
    	
    	try {
    		// prepared statement for insertion
    		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    		
    		// set values
            stmt.setString(1,contact.getName());
            stmt.setString(2,contact.getEmail());
            stmt.setString(3,contact.getAddress());
            stmt.setDate(4, new Date(contact.getBirthday().getTimeInMillis()));
            
            // execute
            stmt.execute();
            
            // Retrieve the id generated during insertion
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
            	contact.setId(generatedKeys.getLong(1));
            }
            
            stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }
    
    public void update(Contact contato) {
    	String sql = "update contacts set name=?, email=?, address=?, " +
    				 "birthday=? where id=?";
    	
    	try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			// set values
            stmt.setString(1,contato.getName());
            stmt.setString(2,contato.getEmail());
            stmt.setString(3,contato.getAddress());
            stmt.setDate(4, new Date(contato.getBirthday().getTimeInMillis()));
            stmt.setLong(5,contato.getId());
            stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }
    
    public void remove(Contact contato) {
    	String sql = "delete from contacts where id=?";
    
    	try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, contato.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Contact> getList() {
    	String sql = "select * from contacts";
    	
    	try {
			List<Contact> contacts = new ArrayList<>();
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				// create a contact object
				Contact contact = new Contact();
                contact.setId(rs.getLong("id"));
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                
                // creating the date with a Calendar
                Calendar date = Calendar.getInstance();
                date.setTime(rs.getDate("birthday"));
                contact.setBirthday(date);
                
                // adding object to the list
                contacts.add(contact);
			}
			
			rs.close();
			stmt.close();
			
			return contacts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }
}

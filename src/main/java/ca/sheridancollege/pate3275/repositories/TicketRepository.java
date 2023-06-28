package ca.sheridancollege.pate3275.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.pate3275.beans.Ticket;
import ca.sheridancollege.pate3275.beans.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TicketRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	public void addTicket(Ticket ticket) {
		MapSqlParameterSource parameters = new MapSqlParameterSource(); 
		
		String query = "INSERT INTO tickets (userName, name, price, date, venue, pet_allowed, parking_allowed) VALUES "
				+ "(:un, :n, :p, :d, :v, :pa, :pka)";
		
		parameters.addValue("un", ticket.getUserName());
		parameters.addValue("n", ticket.getName());
		parameters.addValue("p", ticket.getPrice());
		parameters.addValue("d", ticket.getDate());
		parameters.addValue("v", ticket.getVenue());
		parameters.addValue("pa", ticket.getPetAllowed());
		parameters.addValue("pka", ticket.getParkingAllowed());
		
		jdbc.update(query, parameters);
	}
	
	public List<User> getUsers(){
		
		ArrayList<User> usernames = new ArrayList<User>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM SEC_USER u JOIN USER_ROLE r ON u.userID=r.UserID WHERE r.roleID=2 ORDER BY userName";
		List<Map<String,Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String,Object> row : rows) {
			User u = new User();
			u.setUserId((long)row.get("userId"));
			u.setUserName((String)row.get("userName"));
			usernames.add(u);
		}
		
		
		return usernames;
	}
	
	public ArrayList<Ticket> getTickets(){
	
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM tickets";
		List<Map<String,Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String,Object> row : rows) {
			Ticket t = new Ticket();
			t.setId((int)row.get("id"));
			t.setUserName((String)row.get("userName"));
			t.setName((String)row.get("name"));
			t.setPrice((double)row.get("price"));
			t.setDate((String)row.get("date"));
			t.setVenue((String)row.get("venue"));
			t.setPetAllowed((String)row.get("pet_allowed"));
			t.setParkingAllowed((String)row.get("parking_allowed"));
			tickets.add(t);
		}
		
		
		return tickets;
	}
	
	public Ticket getTicketsById(int id){
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM tickets WHERE id=:meow";
		parameters.addValue("meow", id);
		List<Map<String,Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String,Object> row : rows) {
			Ticket t = new Ticket();
			t.setId((int)row.get("id"));
			t.setName((String)row.get("name"));
			t.setPrice((double)row.get("price"));
			t.setDate((String)row.get("date"));
			t.setVenue((String)row.get("venue"));
			t.setPetAllowed((String)row.get("pet_allowed"));
			t.setPetAllowed((String)row.get("parking_allowed"));
			tickets.add(t);
		}
		
		
		if (tickets.size()>0) {
			return tickets.get(0);
		}else {
			return null;
		}
	}
	
	public void editTicket(Ticket ticket) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "UPDATE tickets SET name=:n, "
				+ "price=:p, date=:d, venue=:v, pet_allowed=:pa, parking_allowed=:pka "
				+"WHERE id=:id";
		
		parameters.addValue("id", ticket.getId());
		parameters.addValue("n", ticket.getName());
		parameters.addValue("p", ticket.getPrice());
		parameters.addValue("d", ticket.getDate());
		parameters.addValue("v", ticket.getVenue());
		parameters.addValue("pa", ticket.getPetAllowed());
		parameters.addValue("pka", ticket.getParkingAllowed());
		
		jdbc.update(query, parameters);
		
	}

public void deleteTicket(Ticket ticket) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "DELETE from tickets WHERE id=:id";
		
		parameters.addValue("id", ticket.getId());

		
		jdbc.update(query, parameters);
		
		String updateQuery = "UPDATE tickets SET id = id - 1 WHERE id > :id";
	    parameters.addValue("id", ticket.getId());
	    jdbc.update(updateQuery, parameters);
	    
	    String resetQuery = "ALTER TABLE tickets ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM tickets) + 1";
	    jdbc.update(resetQuery, new MapSqlParameterSource());
		
	}

public User findUserAccount(String userName) {
	String query = "SELECT * FROM sec_user WHERE username=:name";
	
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("name", userName);
	
	ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters, 
			new BeanPropertyRowMapper<User>(User.class));
	
	return (users.size()>0)?users.get(0):null;
}

public List<String> getRolesById(long userId){
	ArrayList<String> roles = new ArrayList<String>();
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	String query="SELECT user_role.userId, sec_role.roleName FROM user_role, sec_role WHERE user_role.roleId=sec_role.roleId AND "
			+ "userId=:meow";
	parameters.addValue("meow", userId);
	
	List<Map<String,Object>> rows = jdbc.queryForList(query, parameters);
	for(Map<String,Object> row : rows) {
		roles.add((String)row.get("roleName"));
	}
	return roles;
	
}

@Bean
public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

public void addUser(String username, String password) {
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	String query="INSERT INTO sec_user (userName, encryptedPassword, ENABLED) "
			+ "VALUES (:name, :pass, 1)";
	
	parameters.addValue("name", username);
	parameters.addValue("pass", passwordEncoder().encode(password));
	
	jdbc.update(query, parameters);
}

public void addUserRole(long userId, long roleId) {
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	
	String query="INSERT INTO user_role (userId, roleId) "
			+ "VALUES (:uid, :rid)";
	
	parameters.addValue("uid", userId);
	parameters.addValue("rid", roleId);
	
	jdbc.update(query, parameters);
}
	
}

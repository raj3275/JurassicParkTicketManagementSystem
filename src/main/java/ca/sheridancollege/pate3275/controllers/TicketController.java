package ca.sheridancollege.pate3275.controllers;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.pate3275.beans.Ticket;
import ca.sheridancollege.pate3275.beans.User;
import ca.sheridancollege.pate3275.repositories.TicketRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TicketController {
	private TicketRepository ticketRepo;
	
	@GetMapping("/")
	public String home(){
		return "root.html";
	}
	
	@GetMapping("/login")
	public String loadLogin() {
		return "login.html";
	}
	
	@GetMapping("/register")
	public String loadRegistration() {
		return "registration.html";
	}
	
	@PostMapping("/register")
	public String processRegister(@RequestParam String username, @RequestParam String password) {
		
		ticketRepo.addUser(username, password);
		long uid = ticketRepo.findUserAccount(username).getUserId();
		ticketRepo.addUserRole(uid, 2);
		
		return "redirect:/";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		
		model.addAttribute("ticket", new Ticket());
		List<User> usernames = ticketRepo.getUsers(); 
		model.addAttribute("userName", usernames);
		return "add.html";
	}
	
	@GetMapping("/process")
	public String process(@ModelAttribute Ticket ticket, Model model) {
		ticketRepo.addTicket(ticket);
		return "redirect:/add";
	}
	
	@GetMapping("/view")
	public String goView(Model model) {
		model.addAttribute("ticket", ticketRepo.getTickets());
		return "view.html";
	}
	
	@GetMapping("/viewUsers")
	public String goViewUsers(Model model) {
		model.addAttribute("users", ticketRepo.getUsers());
		return "viewusers.html";
	}	
	
	@GetMapping("/edit/{id}")
	public String editTicket(@PathVariable int id, Model model) {
		//get particular ticket based on id
		Ticket ticket = ticketRepo.getTicketsById(id);
		//Send that ticket to an edit page
		model.addAttribute("ticket", ticket);
		return "edit.html";
	}
	
	@PostMapping("/editTicket")
	public String processEdit(@ModelAttribute Ticket ticket) {
//		Ticket t = ticket; //Modified Ticket
		
		//update the ticket in the database
		ticketRepo.editTicket(ticket);
		//Go back to view page
		
		return "redirect:/view";
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteTicket(@ModelAttribute Ticket ticket) {
		//ticket = ticketRepo.getTicketsById(id);
		ticketRepo.deleteTicket(ticket);
		return "redirect:/view";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "accessdenied.html";
	}	
	
}

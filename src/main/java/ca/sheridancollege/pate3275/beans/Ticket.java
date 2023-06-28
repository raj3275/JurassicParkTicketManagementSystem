package ca.sheridancollege.pate3275.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

	private int id;
	private String userName;
	private String name;
	private double price;
	private String date;
	private String venue;
	private String petAllowed;
	private String parkingAllowed;
	
	private String[] paValues= {"Yes", "No"};
	private String[] dates= {"18 Nov", "19 Nov", "20 Nov"};

}

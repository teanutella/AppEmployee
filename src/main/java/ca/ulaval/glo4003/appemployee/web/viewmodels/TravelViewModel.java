package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.Arrays;

import ca.ulaval.glo4003.appemployee.domain.travel.Vehicle;

public class TravelViewModel {

	private double distanceTravelled;
	private String date = "";
	private String userEmail = "";
	private String comment = "";
	private String uId = "";
	private String vehicle = "";
	private ArrayList<Vehicle> availableVehicles = new ArrayList<Vehicle>(Arrays.asList(Vehicle.values()));

	public TravelViewModel(String userEmail) {
		this.userEmail = userEmail;
	}

	public TravelViewModel() {
	}

	public TravelViewModel(String uid, double distance, String comment, String date, String userEmail, String vehicle) {
		this.uId = uid;
		this.distanceTravelled = distance;
		this.comment = comment;
		this.date = date;
		this.userEmail = userEmail;
		this.vehicle = vehicle;
	}

	public double getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public ArrayList<Vehicle> getAvailableVehicles() {
		return availableVehicles;
	}

	public void setAvailableVehicles(ArrayList<Vehicle> availableVehicles) {
		this.availableVehicles = availableVehicles;
	}

}

package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel {
	private String uId;
	private String name;
	private List<String> authorizedUsers = new ArrayList<String>();
	private String selectedUserEmail = "";
	private String currentUserEmail = "";
	private double multiplicativeFactor = 1.0;
	private List<String> availableUsers = new ArrayList<String>();
	
	public TaskViewModel() { 
		
	}

	public TaskViewModel(String uid, String taskName, List<String> authorizedUsers, double multiplicativeFactor) {
		this.uId = uid;
		this.name = taskName;
		this.authorizedUsers = authorizedUsers;
		this.multiplicativeFactor = multiplicativeFactor;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public List<String> getAuthorizedUsers() {
		return authorizedUsers;
	}

	public void setAuthorizedUsers(List<String> authorizedUsers) {
		this.authorizedUsers = authorizedUsers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelectedUserEmail() {
		return selectedUserEmail;
	}
	
	public void setSelectedUserEmail(String selectedUserEmail) {
		this.selectedUserEmail = selectedUserEmail;
	}

	public List<String> getAvailableUsers() {
		return availableUsers;
	}

	public void setAvailableUsers(List<String> availableUsers) {
		this.availableUsers = availableUsers;
	}

	public String getCurrentUserEmail() {
		return currentUserEmail;
	}

	public void setCurrentUserEmail(String currentUserEmail) {
		this.currentUserEmail = currentUserEmail;
	}

	public double getMultiplicativeFactor() {
		return multiplicativeFactor;
	}

	public void setMultiplicativeFactor(double multiplicativeFactor) {
		this.multiplicativeFactor = multiplicativeFactor;
	}

}

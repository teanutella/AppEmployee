package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.travel.Vehicule;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Service
public class UserService {

	private UserRepository userRepository;
	private PayPeriodRepository payPeriodRepository;
	private TaskRepository taskRepository;
	private TimeEntryRepository timeEntryRepository;
	private ExpenseRepository expenseRepository;
	private TravelRepository travelRepository;

	@Autowired
	public UserService(UserRepository userRepository, PayPeriodRepository payPeriodRepository, TaskRepository taskRepository,
			ExpenseRepository expenseRepository, TimeEntryRepository timeEntryRepository, TravelRepository travelRepository) {
		this.userRepository = userRepository;
		this.payPeriodRepository = payPeriodRepository;
		this.taskRepository = taskRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.expenseRepository = expenseRepository;
		this.travelRepository = travelRepository;
	}

	public void updateCurrentPayPeriod(PayPeriod payPeriod) {
		try {
			payPeriodRepository.update(payPeriod);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}

	}

	public List<Task> getTasksForUserForAPayPeriod(PayPeriod payPeriod, String userId) {

		List<Task> tasks = new ArrayList<Task>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
			if (entry != null && entry.getUserEmail().equals(userId)) {
				tasks.add(taskRepository.findByUid(entry.getuId()));
			}
		}

		return tasks;

	}

	public ArrayList<TimeEntry> getTimeEntriesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {

		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);

			if (entry.getUserEmail().equals(userEmail)) {
				timeEntries.add(entry);
			}
		}

		return timeEntries;

	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> findUsersByEmail(List<String> emails) {
		List<User> users = new ArrayList<User>();

		for (String email : emails) {
			User user = userRepository.findByEmail(email);
			users.add(user);
		}

		return users;
	}

	public TimeEntry getTimeEntry(String id) {

		return timeEntryRepository.findByUid(id);
	}

	public void updateTimeEntry(String projectNumber, TimeViewModel viewModel) {
		TimeEntry entry = timeEntryRepository.findByUid(projectNumber);
		entry.setBillableHours(viewModel.getHoursTimeEntry());
		entry.setComment(viewModel.getCommentTimeEntry());
		entry.setDate(new LocalDate(viewModel.getDateTimeEntry()));
		entry.setTaskuId(viewModel.getTaskIdTimeEntry());

		try {
			timeEntryRepository.store(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Expense> getExpensesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {

		ArrayList<Expense> expenses = new ArrayList<Expense>();
		for (Expense expense : expenseRepository.findAll()) {
			if (expense.getUserEmail().equals(userEmail) && expense.getDate().isBefore(payPeriod.getEndDate())
					&& expense.getDate().isAfter(payPeriod.getStartDate())) {
				expenses.add(expense);
			}
		}
		return expenses;
	}

	public List<Travel> getTravelEntriesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {
		ArrayList<Travel> travels = new ArrayList<Travel>();
		for (Travel travel : travelRepository.findAll()) {
			if (travel.getUserEmail().equals(userEmail) && travel.getDate().isBefore(payPeriod.getEndDate())
					&& travel.getDate().isAfter(payPeriod.getStartDate())) {
				travels.add(travel);
			}
		}
		return travels;
	}

	public void updateTravelEntry(String uId, TravelViewModel viewModel) {
		Travel entry = travelRepository.findByUid(uId);
		entry.setComment(viewModel.getComment());
		entry.setDate(new LocalDate(viewModel.getDate()));
		entry.setDistanceTravelled(viewModel.getDistanceTravelled());
		entry.setUserEmail(viewModel.getUserEmail());
		Vehicule v = Vehicule.ENTERPRISE;
		entry.setVehicule(v);

		try {
			travelRepository.store(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}

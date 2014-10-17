package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Component
public class PayPeriodConverter {

	public PayPeriodConverter() {

	}

	public TimeEntry convertToTimeEntry(TimeViewModel payPeriodViewModel) {
		TimeEntry newTimeEntry = new TimeEntry();
		newTimeEntry.setBillableHours(payPeriodViewModel.getHoursTimeEntry());
		newTimeEntry.setDate(payPeriodViewModel.getDateTimeEntry());
		newTimeEntry.setTaskuId(payPeriodViewModel.getTaskIdTimeEntry());
		newTimeEntry.setUserEmail(payPeriodViewModel.getUserEmail());

		return newTimeEntry;
	}

	public TimeViewModel convert(PayPeriod payPeriod, List<TimeEntry> timeEntrys, List<Task> tasks) {
		TimeViewModel payPeriodViewModel = new TimeViewModel();
		payPeriodViewModel.setStartDate(payPeriod.getStartDate().toString());
		payPeriodViewModel.setEndDate(payPeriod.getEndDate().toString());
		payPeriodViewModel.setAvailableTasks(tasks);

		return payPeriodViewModel;
	}
}

package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeEntryViewModel;

@Controller
@RequestMapping(value = "/time")
@SessionAttributes({ "email" })
public class TimeController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String CURRENT_PAYPERIOD_ATTRIBUTE = "payPeriod";
	static final String PREVIOUS_PAYPERIOD_ATTRIBUTE = "previousPayPeriod";

	private TimeService timeService;

	@Autowired
	public TimeController(TimeService timeService) {
		this.timeService = timeService;
	}

	@ModelAttribute(CURRENT_PAYPERIOD_ATTRIBUTE)
	public PayPeriodViewModel currentPayPeriodDates(HttpSession session) {
		return timeService.retrieveCurrentPayPeriodViewModel();
	}

	@ModelAttribute(PREVIOUS_PAYPERIOD_ATTRIBUTE)
	public PayPeriodViewModel previousPayPeriodDates(HttpSession session) {
		return timeService.retrievePreviousPayPeriodViewModel();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showTimeEntriesForm(ModelMap model, HttpSession session) throws PayPeriodNotFoundException, TimeEntryNotFoundException {
		try {
			Collection<TimeEntryViewModel> timeEntriesViewModels = timeService.retrieveAllTimeEntriesViewModelsForCurrentPayPeriod(
					session.getAttribute(EMAIL_ATTRIBUTE).toString());
			model.addAttribute("timeEntries", timeEntriesViewModels);
		} catch (PayPeriodNotFoundException | TimeEntryNotFoundException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "time";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateTimeEntryForm(Model model, TimeEntryViewModel timeViewModel, HttpSession session) {
		model.addAttribute("timeForm", timeService.retrieveTimeEntryViewModelForUser(session.getAttribute(EMAIL_ATTRIBUTE).toString()));
		return "createTimeEntry";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createTimeEntry(Model model, TimeEntryViewModel timeform, HttpSession session) throws Exception {
		try {
			timeService.createTimeEntry(timeform, timeService.retrieveCurrentPayPeriod());	
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}	
		return "redirect:/time";
	}

	@RequestMapping(value = "/{timeEntryUid}/edit", method = RequestMethod.GET)
	public String showEditTimeEntryForm(@PathVariable String timeEntryUid, Model model, HttpSession session) throws TimeEntryNotFoundException {
		try {
			model.addAttribute("timeForm", timeService.retrieveTimeEntryViewModel(timeEntryUid));	
		} catch (TimeEntryNotFoundException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "editTimeEntry";
	}

	@RequestMapping(value = "/{timeEntryUid}/edit", method = RequestMethod.POST)
	public String editTimeEntry(@PathVariable String timeEntryUid, Model model, TimeEntryViewModel viewModel, HttpSession session) throws Exception {
		try {
			timeService.updateTimeEntry(viewModel);	
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}	
		return "redirect:/time";
	}

	@RequestMapping(value = "/previousTime", method = RequestMethod.GET)
	public String showPreviousTimeForm(ModelMap model, HttpSession session) throws TimeEntryNotFoundException {
		try {
			Collection<TimeEntryViewModel> timeEntriesViewModels = timeService.retrieveAllTimeEntriesViewModelsForPreviousPayPeriod(
					session.getAttribute(EMAIL_ATTRIBUTE).toString());
			model.addAttribute("timeEntries", timeEntriesViewModels);
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}	
		return "previousTime";
	}

	@RequestMapping(value = "/previousTime/add", method = RequestMethod.GET)
	public String showCreatePreviousTimeEntryForm(Model model, TimeEntryViewModel timeViewModel, HttpSession session) {
		model.addAttribute("timeForm", timeService.retrieveTimeEntryViewModelForUser(session.getAttribute(EMAIL_ATTRIBUTE).toString()));
		return "createPreviousTimeEntry";
	}

	@RequestMapping(value = "/previousTime/add", method = RequestMethod.POST)
	public String createPreviousTimeEntry(Model model, TimeEntryViewModel timeViewModel, HttpSession session) throws Exception {
		try {
			timeService.createTimeEntry(timeViewModel, timeService.retrievePreviousPayPeriod());
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "redirect:/time/previousTime";
	}

	@RequestMapping(value = "/previousTime/{timeEntryUid}/edit", method = RequestMethod.GET)
	public String showEditPreviousTimeEntryForm(@PathVariable String timeEntryUid, Model model, HttpSession session) throws TimeEntryNotFoundException {
		try {
			model.addAttribute("timeForm", timeService.retrieveTimeEntryViewModel(timeEntryUid));
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "editPreviousTimeEntry";
	}

	@RequestMapping(value = "/previousTime/{timeEntryUid}/edit", method = RequestMethod.POST)
	public String editPreviousTimeEntry(@PathVariable String timeEntryUid, Model model, TimeEntryViewModel viewModel, HttpSession session) throws Exception {
		try {
			timeService.updateTimeEntry(viewModel);
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "redirect:/time/previousTime";
	}

}

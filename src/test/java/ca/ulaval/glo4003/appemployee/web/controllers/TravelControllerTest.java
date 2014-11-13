package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

public class TravelControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String TRAVEL_UID = "uid";
	private static final LocalDate START_DATE = new LocalDate("2014-10-13");
	private static final LocalDate END_DATE = new LocalDate("2014-10-26");
	private static final String TRAVEL_JSP = "travel";
	private static final String SIMPLE_REDIRECT = "redirect:/";
	private static final String CREATE_TRAVEL_JSP = "createTravelEntry";
	private static final String TRAVEL_ENTRY_SUBMIT_JSP = "travelEntrySubmitted";
	private static final String VEHICLE = "ENTERPRISE";
	private static final String VEHICLE_ERROR_REDIRECT = "redirect:/travel/errorNoVehiculeSelected";
	private static final String EDIT_TRAVEL_ENTRY_JSP = "editTravelEntry";
	private static final String TRAVEL_REDIRECT = "redirect:/travel/";
	private static final String NO_VEHICULE_SELECTED_JSP = "noVehiculeSelectedError";

	private TravelController travelController;
	private PayPeriodService payPeriodServiceMock;
	private TaskRepository taskRepositoryMock;
	private UserService userServiceMock;
	private TravelConverter travelConverterMock;
	private TravelService travelServiceMock;
	private HttpSession sessionMock;
	private PayPeriod payPeriodMock;
	private ModelMap modelMapMock;
	private Model modelMock;
	private TravelViewModel travelViewModelMock;
	private Travel travelMock;
	private List<Travel> travels = new ArrayList<Travel>();

	@Before
	public void init() {
		payPeriodServiceMock = mock(PayPeriodService.class);
		taskRepositoryMock = mock(TaskRepository.class);
		userServiceMock = mock(UserService.class);
		travelConverterMock = mock(TravelConverter.class);
		travelServiceMock = mock(TravelService.class);
		payPeriodMock = mock(PayPeriod.class);
		sessionMock = mock(HttpSession.class);
		modelMapMock = mock(ModelMap.class);
		modelMock = mock(Model.class);
		travelViewModelMock = mock(TravelViewModel.class);
		travelMock = mock(Travel.class);
		travelController = new TravelController(payPeriodServiceMock, taskRepositoryMock, userServiceMock, travelConverterMock, travelServiceMock);
	}

	@Test
	public void getTravelReturnsTravelFormIfSuccessful() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(userServiceMock.getTravelEntriesForUserForAPayPeriod(payPeriodMock, VALID_EMAIL)).thenReturn(travels);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		String returnedForm = travelController.getTravel(modelMapMock, sessionMock);

		assertEquals(TRAVEL_JSP, returnedForm);
	}

	@Test
	public void getTravelReturnsRedirectLinkWhenMissingAttribute() {
		String returnedForm = travelController.getTravel(modelMapMock, sessionMock);
		assertEquals(SIMPLE_REDIRECT, returnedForm);
	}

	@Test
	public void createTravelEntryReturnsCreationFormIfSuccessful() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		String returnedForm = travelController.createTravelEntry(modelMock, travelViewModelMock, sessionMock);

		assertEquals(CREATE_TRAVEL_JSP, returnedForm);
	}

	@Test
	public void createTravelEntryReturnsRedirectLinkIfMissingAttribute() {
		String returnedForm = travelController.createTravelEntry(modelMock, travelViewModelMock, sessionMock);
		assertEquals(SIMPLE_REDIRECT, returnedForm);
	}

	@Test
	public void saveTravelEntryReturnsSaveFormIfSuccessful() throws Exception {
		when(travelViewModelMock.getVehicule()).thenReturn(VEHICLE);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = travelController.saveTravelEntry(modelMock, travelViewModelMock, sessionMock);
		assertEquals(TRAVEL_ENTRY_SUBMIT_JSP, returnedForm);
	}

	@Test
	public void saveTravelEntryReturnsErrorRedirectLinkIfMissingVehicle() throws Exception {
		when(travelViewModelMock.getVehicule()).thenReturn("NONE");
		String returnedForm = travelController.saveTravelEntry(modelMock, travelViewModelMock, sessionMock);
		assertEquals(VEHICLE_ERROR_REDIRECT, returnedForm);
	}

	@Test
	public void saveTravelEntryCallsStoreMethod() throws Exception {
		when(travelViewModelMock.getVehicule()).thenReturn(VEHICLE);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(travelConverterMock.convert(travelViewModelMock)).thenReturn(travelMock);
		travelController.saveTravelEntry(modelMock, travelViewModelMock, sessionMock);
		verify(travelServiceMock, times(1)).store(travelMock);
	}

	@Test
	public void editTravelEntryReturnsEditFormIfSuccessful() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(travelServiceMock.findByuId(TRAVEL_UID)).thenReturn(travelMock);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		when(travelConverterMock.convert(travelMock)).thenReturn(travelViewModelMock);

		String returnedForm = travelController.editTravelEntry(TRAVEL_UID, modelMock, sessionMock);

		assertEquals(EDIT_TRAVEL_ENTRY_JSP, returnedForm);
	}

	@Test
	public void editTravelEntryReturnsRedirectLinkIfMissingAttribute() throws Exception {
		String returnedForm = travelController.editTravelEntry(TRAVEL_UID, modelMock, sessionMock);
		assertEquals(SIMPLE_REDIRECT, returnedForm);
	}

	@Test
	public void saveEditedTravelEntryReturnsValidRedirectLinkIfSuccessFul() throws Exception {
		when(travelViewModelMock.getVehicule()).thenReturn(VEHICLE);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = travelController.saveEditedTravelEntry(TRAVEL_UID, travelViewModelMock, sessionMock);
		assertEquals(TRAVEL_REDIRECT, returnedForm);
	}

	@Test
	public void saveEditedTravelEntryReturnsErrorRedirectIfNoVehicle() throws Exception {
		when(travelViewModelMock.getVehicule()).thenReturn("NONE");
		String returnedForm = travelController.saveEditedTravelEntry(TRAVEL_UID, travelViewModelMock, sessionMock);
		assertEquals(VEHICLE_ERROR_REDIRECT, returnedForm);
	}

	@Test
	public void saveEditedTravelEntryCallsUpdateMethod() throws Exception {
		when(travelViewModelMock.getVehicule()).thenReturn(VEHICLE);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		travelController.saveEditedTravelEntry(TRAVEL_UID, travelViewModelMock, sessionMock);
		verify(travelServiceMock, times(1)).update(TRAVEL_UID, travelViewModelMock);
	}

	@Test
	public void getErrorNoVehiculeSelectedReturnsNoVehicleForm() {
		String returnedForm = travelController.getErrorNoVehiculeSelected(modelMapMock, sessionMock);
		assertEquals(NO_VEHICULE_SELECTED_JSP, returnedForm);
	}

}
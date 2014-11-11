package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.appemployee.services.DepartmentService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.DepartmentConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class DepartmentControllerTest {

	private static final String REDIRECT_LINK = "redirect:/";
	private static final String EMAIL_ATTRIBUTE = "email";
	private static final String DEPARTMENT_NAME = "dummyDepartment";
	private static final String EMAIL = "test@test.com";

	@Mock
	private DepartmentService departmentServiceMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private UserConverter userConverterMock;

	@Mock
	private DepartmentConverter departmentConverterMock;

	@Mock
	private Model modelMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private UserViewModel userViewModelMock;

	@InjectMocks
	private DepartmentController departmentController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		departmentController = new DepartmentController(departmentServiceMock, userServiceMock, userConverterMock, departmentConverterMock);
	}

	@Test
	public void showDepartmentsListRedirectsToLoginPageWhenSessionAttributeIsNull() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(null);
		String form = departmentController.showDepartmentsList(modelMock, sessionMock);
		assertEquals(REDIRECT_LINK, form);
	}

	@Test
	public void showDepartmentsListReturnsDepartmentsListFormWhenSessionAttributeNotNull() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		String form = departmentController.showDepartmentsList(modelMock, sessionMock);
		assertEquals("departmentsList", form);
	}

	@Test
	public void showEmployeesListRedirectsToLoginPageWhenSessionAttributeIsNull() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(null);
		String form = departmentController.showEmployeesList(DEPARTMENT_NAME, modelMock, sessionMock);
		assertEquals(REDIRECT_LINK, form);
	}

	@Test
	public void showCreateEmployeeAccountPageRedirectsToLoginPageWhenSessionAttributeIsNull() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(null);
		String form = departmentController.showCreateEmployeeAccountPage(DEPARTMENT_NAME, modelMock, userViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, form);
	}

}

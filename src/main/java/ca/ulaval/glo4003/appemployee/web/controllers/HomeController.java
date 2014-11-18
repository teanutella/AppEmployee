package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

@Controller
@SessionAttributes({ "email", "role" })
public class HomeController {

	private UserRepository userRepository;
	static final String EMAIL_ATTRIBUTE = "email";
	static final String ROLE_ATTRIBUTE = "role";
	static final String HOME_VIEW = "home";
	static final String LOGIN_FORM_ATTRIBUTE = "loginForm";
	static final String SIMPLE_REDIRECT = "redirect:/";

	@Autowired
	public HomeController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@ModelAttribute("loginForm")
	public LoginFormViewModel defaultUser() {
		return new LoginFormViewModel();
	}

	@RequestMapping("/")
	public String displayLoginForm() {
		return HOME_VIEW;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(LoginFormViewModel form, ModelMap model) {

		User user = userRepository.findByEmail(form.getEmail());
		if (user != null && user.validatePassword(form.getPassword())) {
			model.addAttribute(EMAIL_ATTRIBUTE, form.getEmail());
			model.addAttribute(ROLE_ATTRIBUTE, user.getRole());

			return new ModelAndView(HOME_VIEW, model);
		} else {
			model.addAttribute("alert", "Invalid username and/or password.");
			model.addAttribute(LOGIN_FORM_ATTRIBUTE, form);
			return new ModelAndView(HOME_VIEW);
		}
	}

	@RequestMapping(value = "**/logout")
	public String logout(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		model.clear();
		return SIMPLE_REDIRECT;
	}
}

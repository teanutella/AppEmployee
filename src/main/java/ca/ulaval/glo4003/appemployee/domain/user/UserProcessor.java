package ca.ulaval.glo4003.appemployee.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;

@Component
public class UserProcessor {

	private UserRepository userRepository;

	@Autowired
	public UserProcessor(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User retrieveUserByEmail(String email) throws UserNotFoundException {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException("User not found with following email : " + email);
		}
		return user;
	}

	public void updateUser(String email, String password, Role role, double wage) throws Exception {
		User user = new User(email, password, role, wage);
		userRepository.store(user);
	}

	public boolean validateUserCredentials(String userEmail, String password) {
		User user = retrieveUserByEmail(userEmail);
		return user.validatePassword(password);
	}

	public Role retrieveUserRole(String userEmail) {
		return retrieveUserByEmail(userEmail).getRole();
	}

}

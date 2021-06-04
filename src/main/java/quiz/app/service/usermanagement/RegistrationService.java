package quiz.quiz_app.service.usermanagement;

import quiz.quiz_app.model.User;

public interface RegistrationService {
	User startRegistration(User user);

	User continueRegistration(User user, String token);

	boolean isRegistrationCompleted(User user);
}

package quiz.quiz_app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import quiz.quiz_app.exceptions.ResourceUnavailableException;
import quiz.quiz_app.exceptions.UnauthorizedActionException;
import quiz.quiz_app.exceptions.UserAlreadyExistsException;
import quiz.quiz_app.model.User;

public interface UserService extends UserDetailsService {
	User saveUser(User user) throws UserAlreadyExistsException;

	User find(Long id) throws ResourceUnavailableException;;

	User findByEmail(String email) throws ResourceUnavailableException;

	User findByUsername(String username) throws ResourceUnavailableException;

	User updatePassword(User user, String password) throws ResourceUnavailableException;

	void delete(Long user_id) throws UnauthorizedActionException, ResourceUnavailableException;

	User setRegistrationCompleted(User user) throws ResourceUnavailableException;

	boolean isRegistrationCompleted(User user) throws ResourceUnavailableException;

}
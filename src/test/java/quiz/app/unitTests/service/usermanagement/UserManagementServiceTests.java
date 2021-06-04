package quiz.quiz_app.unitTests.service.usermanagement;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import quiz.quiz_app.exceptions.InvalidTokenException;
import quiz.quiz_app.model.ForgotPasswordToken;
import quiz.quiz_app.model.TokenType;
import quiz.quiz_app.model.User;
import quiz.quiz_app.service.UserService;
import quiz.quiz_app.service.usermanagement.UserManagementService;
import quiz.quiz_app.service.usermanagement.UserManagementServiceImpl;
import quiz.quiz_app.service.usermanagement.token.TokenDeliverySystem;
import quiz.quiz_app.service.usermanagement.token.TokenServiceForgotPassword;

public class UserManagementServiceTests {
	private static final String TOKEN = "token";

	UserManagementService userManagementService;

	// Mocks
	TokenServiceForgotPassword tokenService;
	TokenDeliverySystem tokenDeliverySystem;
	UserService userService;

	// Models
	User user = new User();
	ForgotPasswordToken token = new ForgotPasswordToken();;

	@Before
	public void before() {
		userService = mock(UserService.class);
		tokenService = mock(TokenServiceForgotPassword.class);
		tokenDeliverySystem = mock(TokenDeliverySystem.class);

		userManagementService = new UserManagementServiceImpl(userService, tokenService, tokenDeliverySystem);

		user.setEmail("a@a.com");
		user.setPassword("Password");

	}

	@Test
	public void resendPassword() {

		when(tokenService.generateTokenForUser(user)).thenReturn(token);

		userManagementService.resendPassword(user);

		verify(tokenDeliverySystem, times(1)).sendTokenToUser(token, user, TokenType.FORGOT_PASSWORD);

	}

	@Test(expected = InvalidTokenException.class)
	public void validateInvalidToken() {
		doThrow(new InvalidTokenException()).when(tokenService).validateTokenForUser(user, TOKEN);

		userManagementService.verifyResetPasswordToken(user, TOKEN);
	}

	@Test
	public void validateValidToken() {
		userManagementService.verifyResetPasswordToken(user, TOKEN);

		verify(tokenService, times(1)).validateTokenForUser(user, TOKEN);
	}

	@Test
	public void updatePassword() {
		userManagementService.updatePassword(user, "pass");

		verify(userService, times(1)).updatePassword(user, "pass");
	}
}

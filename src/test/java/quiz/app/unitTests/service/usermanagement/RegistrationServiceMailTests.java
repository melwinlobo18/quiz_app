package quiz.quiz_app.unitTests.service.usermanagement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import quiz.quiz_app.exceptions.InvalidTokenException;
import quiz.quiz_app.exceptions.UserAlreadyExistsException;
import quiz.quiz_app.model.RegistrationToken;
import quiz.quiz_app.model.TokenModel;
import quiz.quiz_app.model.TokenType;
import quiz.quiz_app.model.User;
import quiz.quiz_app.service.UserService;
import quiz.quiz_app.service.usermanagement.RegistrationService;
import quiz.quiz_app.service.usermanagement.RegistrationServiceMail;
import quiz.quiz_app.service.usermanagement.token.TokenDeliverySystem;
import quiz.quiz_app.service.usermanagement.token.TokenServiceRegistration;

public class RegistrationServiceMailTests {

	private static final String TOKEN = "token";

	RegistrationService registrationService;

	// Mocks
	UserService userService;
	TokenServiceRegistration tokenService;
	TokenDeliverySystem tokenDeliverySystem;

	// Models
	User user = new User();

	@Before
	public void before() {
		userService = mock(UserService.class);
		tokenService = mock(TokenServiceRegistration.class);
		tokenDeliverySystem = mock(TokenDeliverySystem.class);

		registrationService = new RegistrationServiceMail(userService, tokenService, tokenDeliverySystem);

		user.setEmail("a@a.com");
		user.setPassword("Password");
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void startRegistrationWithRegisteredExistingUser_shouldThrowException() {
		when(userService.saveUser(user)).thenThrow(new UserAlreadyExistsException());
		when(userService.findByEmail(user.getEmail())).thenReturn(user);
		when(userService.isRegistrationCompleted(user)).thenReturn(true);

		registrationService.startRegistration(user);
	}

	@Test
	public void startRegistrationWithNonFullyRegisteredUser_shouldntThrowException() {
		when(userService.saveUser(user)).thenThrow(new UserAlreadyExistsException());
		when(userService.findByEmail(user.getEmail())).thenReturn(user);
		when(userService.isRegistrationCompleted(user)).thenReturn(false);

		registrationService.startRegistration(user);

		verify(tokenService, times(1)).generateTokenForUser(user);
		verify(tokenDeliverySystem, times(1)).sendTokenToUser(any(TokenModel.class), eq(user),
				eq(TokenType.REGISTRATION_MAIL));
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void startRegistrationWithFullyRegisteredUser_shouldThrowException() {
		when(userService.saveUser(user)).thenThrow(new UserAlreadyExistsException());
		when(userService.findByEmail(user.getEmail())).thenReturn(user);
		when(userService.isRegistrationCompleted(user)).thenReturn(true);

		registrationService.startRegistration(user);

		verify(tokenService, times(1)).generateTokenForUser(user);
		verify(tokenDeliverySystem, times(1)).sendTokenToUser(any(TokenModel.class), eq(user),
				eq(TokenType.REGISTRATION_MAIL));
	}

	@Test
	public void startRegistrationWithNewUser_shouldCreateToken() {
		when(userService.saveUser(user)).thenReturn(user);
		when(tokenService.generateTokenForUser(user)).thenReturn(new RegistrationToken());

		registrationService.startRegistration(user);

		verify(tokenService, times(1)).generateTokenForUser(user);
		verify(tokenDeliverySystem, times(1)).sendTokenToUser(any(TokenModel.class), eq(user),
				eq(TokenType.REGISTRATION_MAIL));
	}

	@Test(expected = InvalidTokenException.class)
	public void continueRegistrationWithInvalidToken_shouldThrowException() {
		doThrow(new InvalidTokenException()).when(tokenService).validateTokenForUser(user, TOKEN);

		registrationService.continueRegistration(user, TOKEN);
	}

	@Test
	public void continueRegistration_shouldEnableUserAndDestroyToken() {
		registrationService.continueRegistration(user, TOKEN);

		verify(userService, times(1)).setRegistrationCompleted(user);
		verify(tokenService, times(1)).invalidateToken(TOKEN);
	}

	@Test
	public void registrationIsCompleted() {
		when(userService.isRegistrationCompleted(user)).thenReturn(true);

		Boolean isCompleted = registrationService.isRegistrationCompleted(user);

		assertEquals(true, isCompleted);
	}

	@Test
	public void registrationIsNotCompleted() {
		when(userService.isRegistrationCompleted(user)).thenReturn(false);

		Boolean isCompleted = registrationService.isRegistrationCompleted(user);

		assertEquals(false, isCompleted);
	}

}

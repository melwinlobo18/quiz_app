package quiz.quiz_app.unitTests.service.usermanagement;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;

import quiz.quiz_app.model.TokenModel;
import quiz.quiz_app.model.TokenType;
import quiz.quiz_app.model.User;
import quiz.quiz_app.service.usermanagement.token.TokenDeliverySystem;
import quiz.quiz_app.service.usermanagement.token.TokenDeliverySystemConsole;

public class TokenDeliverySystemConsoleTests {

	private static final String CONFIG_URI = "quiz_app.tokens.%s.url";
	private static final String TOKEN = "token";

	TokenDeliverySystem tokenDeliverySystem;

	// Mocks
	Environment env;
	TokenModel token;

	// Models
	User user = new User();

	@Before
	public void before() {
		env = mock(Environment.class);
		token = mock(TokenModel.class);

		tokenDeliverySystem = new TokenDeliverySystemConsole(env);

		token.setToken(TOKEN);
		user.setId(1l);
	}

	/*
	 * Dummy tests to make sure there are no exceptions thrown.
	 */
	@Test
	public void sendEmailRegistrationToken() {
		String registrationConfigUri = String.format(CONFIG_URI, TokenType.REGISTRATION_MAIL.toString().toLowerCase());
		doReturn(TOKEN).when(token).getToken();
		doReturn("dummyUrl/%1$d/%2$s").when(env).getProperty(registrationConfigUri);

		tokenDeliverySystem.sendTokenToUser(token, user, TokenType.REGISTRATION_MAIL);

		verify(env, times(1)).getProperty(registrationConfigUri);
	}

	@Test
	public void sendForgotPasswordToken() {
		String forgotPasswordConfigUri = String.format(CONFIG_URI, TokenType.FORGOT_PASSWORD.toString().toLowerCase());
		doReturn(TOKEN).when(token).getToken();
		doReturn("dummyUrl/%1$d/%2$s").when(env).getProperty(forgotPasswordConfigUri);

		tokenDeliverySystem.sendTokenToUser(token, user, TokenType.FORGOT_PASSWORD);

		verify(env, times(1)).getProperty(forgotPasswordConfigUri);
	}
}

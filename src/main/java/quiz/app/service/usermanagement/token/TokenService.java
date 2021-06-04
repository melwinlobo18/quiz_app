package quiz.quiz_app.service.usermanagement.token;

import java.util.Date;

import quiz.quiz_app.exceptions.InvalidTokenException;
import quiz.quiz_app.model.TokenModel;
import quiz.quiz_app.model.User;

public interface TokenService<T extends TokenModel> {
	T generateTokenForUser(User user);

	void validateTokenForUser(User user, String token) throws InvalidTokenException;

	void invalidateToken(String token);

	void invalidateExpiredTokensPreviousTo(Date date);
}

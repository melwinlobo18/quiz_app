package quiz.quiz_app.service.usermanagement.token;

import org.springframework.scheduling.annotation.Async;

import quiz.quiz_app.model.TokenModel;
import quiz.quiz_app.model.TokenType;
import quiz.quiz_app.model.User;

public interface TokenDeliverySystem {
	@Async
	void sendTokenToUser(TokenModel token, User user, TokenType tokenType);
}

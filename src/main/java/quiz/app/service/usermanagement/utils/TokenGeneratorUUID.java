package quiz.quiz_app.service.usermanagement.utils;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TokenGeneratorUUID implements TokenGenerator {

	@Override
	public String generateRandomToken() {
		return UUID.randomUUID().toString();
	}

}

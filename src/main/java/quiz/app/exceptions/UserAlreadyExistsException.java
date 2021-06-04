package quiz.quiz_app.exceptions;

public class UserAlreadyExistsException extends QuizAppException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}

package quiz.quiz_app.exceptions;

public class InvalidParametersException extends QuizAppException {

	private static final long serialVersionUID = 1L;

	public InvalidParametersException() {
		super();
	}

	public InvalidParametersException(String message) {
		super(message);
	}
}

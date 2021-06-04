package quiz.quiz_app.exceptions;

public class ResourceUnavailableException extends QuizAppException {

	private static final long serialVersionUID = 1L;

	public ResourceUnavailableException() {
		super();
	}

	public ResourceUnavailableException(String message) {
		super(message);
	}
}

package quiz.quiz_app.exceptions;

public class ModelVerificationException extends QuizAppException {

	private static final long serialVersionUID = 1L;

	public ModelVerificationException() {
		super();
	}

	public ModelVerificationException(String message) {
		super(message);
	}
}

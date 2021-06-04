package quiz.quiz_app.exceptions;

public class QuizAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QuizAppException() {
		super();
	}

	public QuizAppException(String message) {
		super(message);
	}
}

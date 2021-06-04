package quiz.quiz_app.exceptions;

public class ActionRefusedException extends QuizAppException {

	private static final long serialVersionUID = 1L;

	public ActionRefusedException() {
		super();
	}

	public ActionRefusedException(String message) {
		super(message);
	}
}
package quiz.quiz_app.service.accesscontrol.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import quiz.quiz_app.model.Quiz;
import quiz.quiz_app.service.accesscontrol.AccessControlService;

@Aspect
@Component
public class AccessControlAspectsQuiz {

	@Autowired
	private AccessControlService<Quiz> accessControlService;

	public void setAccessControlService(AccessControlService<Quiz> accessControlService) {
		this.accessControlService = accessControlService;
	}

	@Around("execution(* quiz.quiz_app.repository.QuizRepository.save(..)) && args(quiz,..)")
	public Object save(ProceedingJoinPoint proceedingJoinPoint, Quiz quiz) throws Throwable {
		if (quiz.getId() == null) {
			accessControlService.canCurrentUserCreateObject(quiz);
		} else {
			accessControlService.canCurrentUserUpdateObject(quiz);
		}

		return proceedingJoinPoint.proceed();
	}

	@Around("execution(* quiz.quiz_app.repository.QuizRepository.find(Long)) && args(id)")
	public Object find(ProceedingJoinPoint proceedingJoinPoint, Long id) throws Throwable {
		accessControlService.canCurrentUserReadObject(id);

		return proceedingJoinPoint.proceed();
	}

	@Around("execution(* quiz.quiz_app.repository.QuizRepository.findAll())")
	public Object findAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		accessControlService.canCurrentUserReadAllObjects();
		return proceedingJoinPoint.proceed();
	}

	@Around("execution(* quiz.quiz_app.repository.QuizRepository.delete(..)) && args(quiz)")
	public Object delete(ProceedingJoinPoint proceedingJoinPoint, Quiz quiz) throws Throwable {
		accessControlService.canCurrentUserDeleteObject(quiz);

		return proceedingJoinPoint.proceed();
	}

}

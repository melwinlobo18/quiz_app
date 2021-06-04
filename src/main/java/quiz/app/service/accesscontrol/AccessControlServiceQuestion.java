package quiz.quiz_app.service.accesscontrol;

import org.springframework.stereotype.Service;

import quiz.quiz_app.model.Question;

@Service
public class AccessControlServiceQuestion extends AccessControlServiceUserOwned<Question> {

}

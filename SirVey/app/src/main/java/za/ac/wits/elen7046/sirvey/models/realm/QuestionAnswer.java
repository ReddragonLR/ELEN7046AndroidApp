package za.ac.wits.elen7046.sirvey.models.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

public class QuestionAnswer extends RealmObject {
    private String question;
    private String answerType;
    private String mongoId;
    private String answer; /// Does this field need to exist?
    private RealmList<RealmString> answerOptions;

    public QuestionAnswer() {
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public RealmList<RealmString> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(RealmList<RealmString> answerOptions) {
        this.answerOptions = answerOptions;
    }
}
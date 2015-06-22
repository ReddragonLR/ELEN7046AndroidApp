package za.ac.wits.elen7046.sirvey.models.realm;

import io.realm.RealmObject;



public class CompletedQuestionAnswer extends RealmObject
{
    private String Question;
    private String AnswerType;
    private String Answer;


    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getAnswerType() {
        return AnswerType;
    }

    public void setAnswerType(String AnswerType) {
        this.AnswerType = AnswerType;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }

}

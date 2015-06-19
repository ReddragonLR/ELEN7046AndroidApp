package za.ac.wits.elen7046.sirvey.models.retrofit;

import com.google.gson.annotations.Expose;

public class CompletedQuestionAnswer {
    @Expose
    private String Question;
    @Expose
    private String AnswerType;
    @Expose
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
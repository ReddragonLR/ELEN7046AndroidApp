package za.ac.wit.elen7046.sirveyapp.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionAnswer {

    @Expose
    private String Question;
    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private String Answer;
    @Expose
    private List<Object> AnswerOptions = new ArrayList<Object>();
    @Expose
    private List<String> AnswerType = new ArrayList<String>();

    /**
     * @return The Question
     */
    public String getQuestion() {
        return Question;
    }

    /**
     * @param Question The Question
     */
    public void setQuestion(String Question) {
        this.Question = Question;
    }

    /**
     * @return The Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return The Answer
     */
    public String getAnswer() {
        return Answer;
    }

    /**
     * @param Answer The Answer
     */
    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }

    /**
     * @return The AnswerOptions
     */
    public List<Object> getAnswerOptions() {
        return AnswerOptions;
    }

    /**
     * @param AnswerOptions The AnswerOptions
     */
    public void setAnswerOptions(List<Object> AnswerOptions) {
        this.AnswerOptions = AnswerOptions;
    }

    /**
     * @return The AnswerType
     */
    public List<String> getAnswerType() {
        return AnswerType;
    }

    /**
     * @param AnswerType The AnswerType
     */
    public void setAnswerType(List<String> AnswerType) {
        this.AnswerType = AnswerType;
    }
}
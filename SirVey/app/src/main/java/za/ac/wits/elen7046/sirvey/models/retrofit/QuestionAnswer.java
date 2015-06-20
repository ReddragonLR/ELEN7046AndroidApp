package za.ac.wits.elen7046.sirvey.models.retrofit;


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionAnswer {

    @Expose
    private String Question;
    @Expose
    private String AnswerType;
    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private String Answer; /// Does this field need to exist?
    @Expose
    private List<String> AnswerOptions = new ArrayList<>();

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

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }

    public List<String> getAnswerOptions() {
        return AnswerOptions;
    }

    public void setAnswerOptions(List<String> AnswerOptions) {
        this.AnswerOptions = AnswerOptions;
    }
}
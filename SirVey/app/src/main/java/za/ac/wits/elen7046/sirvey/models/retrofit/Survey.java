package za.ac.wits.elen7046.sirvey.models.retrofit;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Survey {
    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private String CreatedDate;
    @Expose
    private String CreatedBy;
    @Expose
    private String State;
    @Expose
    private String Name;
    @SerializedName("__v")
    @Expose
    private int V;
    @Expose
    private List<QuestionAnswer> QuestionAnswers = new ArrayList<QuestionAnswer>();

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }
    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getV() {
        return V;
    }

    public void setV(int V) {
        this.V = V;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return QuestionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> QuestionAnswers) {
        this.QuestionAnswers = QuestionAnswers;
    }
}
package za.ac.wit.elen7046.sirveyapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Survey {

    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private String CreatedDate;
    @Expose
    private String CreatedBy;
    @Expose
    private String Name;
    @SerializedName("__v")
    @Expose
    private Integer V;
    @Expose
    private List<String> State = new ArrayList<String>();
    @Expose
    private List<QuestionAnswer> QuestionAnswers = new ArrayList<QuestionAnswer>();

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The CreatedDate
     */
    public String getCreatedDate() {
        return CreatedDate;
    }

    /**
     *
     * @param CreatedDate
     * The CreatedDate
     */
    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    /**
     *
     * @return
     * The CreatedBy
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     *
     * @param CreatedBy
     * The CreatedBy
     */
    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The V
     */
    public Integer getV() {
        return V;
    }

    /**
     *
     * @param V
     * The __v
     */
    public void setV(Integer V) {
        this.V = V;
    }

    /**
     *
     * @return
     * The State
     */
    public List<String> getState() {
        return State;
    }

    /**
     *
     * @param State
     * The State
     */
    public void setState(List<String> State) {
        this.State = State;
    }

    /**
     *
     * @return
     * The QuestionAnswers
     */
    public List<QuestionAnswer> getQuestionAnswers() {
        return QuestionAnswers;
    }

    /**
     *
     * @param QuestionAnswers
     * The QuestionAnswers
     */
    public void setQuestionAnswers(List<QuestionAnswer> QuestionAnswers) {
        this.QuestionAnswers = QuestionAnswers;
    }
}

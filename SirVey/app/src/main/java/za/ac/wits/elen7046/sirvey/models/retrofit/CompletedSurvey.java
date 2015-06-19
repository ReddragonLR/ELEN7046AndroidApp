package za.ac.wits.elen7046.sirvey.models.retrofit;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class CompletedSurvey {
    @Expose
    private String SurveyTaker;
    @Expose
    private String SurveySupervisor;
    @Expose
    private String SurveyName;
    @Expose
    private List<CompletedQuestionAnswer> CompletedQuestionAnswers = new ArrayList<CompletedQuestionAnswer>();

    public String getSurveyTaker() {
        return SurveyTaker;
    }

    public void setSurveyTaker(String SurveyTaker) {
        this.SurveyTaker = SurveyTaker;
    }

    public String getSurveySupervisor() {
        return SurveySupervisor;
    }

    public void setSurveySupervisor(String SurveySupervisor) {
        this.SurveySupervisor = SurveySupervisor;
    }

    public String getSurveyName() {
        return SurveyName;
    }

    public void setSurveyName(String SurveyName) {
        this.SurveyName = SurveyName;
    }

    public List<CompletedQuestionAnswer> getCompletedQuestionAnswers() {
        return CompletedQuestionAnswers;
    }

    public void setCompletedQuestionAnswers(List<CompletedQuestionAnswer> CompletedQuestionAnswers) {
        this.CompletedQuestionAnswers = CompletedQuestionAnswers;
    }

}
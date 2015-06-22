package za.ac.wits.elen7046.sirvey.models.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

public class CompletedSurvey extends RealmObject {

    private String SurveyTaker;

    private String SurveySupervisor;

    private String SurveyName;

    private RealmList<CompletedQuestionAnswer> CompletedQuestionAnswers;

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

    public RealmList<CompletedQuestionAnswer> getCompletedQuestionAnswers() {
        return CompletedQuestionAnswers;
    }

    public void setCompletedQuestionAnswers(RealmList<CompletedQuestionAnswer> CompletedQuestionAnswers) {
        this.CompletedQuestionAnswers = CompletedQuestionAnswers;
    }


}

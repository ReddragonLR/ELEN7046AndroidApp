package za.ac.wits.elen7046.sirvey.models.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Survey extends RealmObject {
    private String name;
    private String mongoId;
    private RealmList<QuestionAnswer> questionAnswers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(RealmList<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
}
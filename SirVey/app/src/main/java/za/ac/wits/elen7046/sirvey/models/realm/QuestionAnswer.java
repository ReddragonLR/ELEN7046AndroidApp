package za.ac.wits.elen7046.sirvey.models.realm;

import io.realm.RealmObject;

public class QuestionAnswer extends RealmObject {
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
package za.ac.wits.elen7046.sirvey.models;

import java.util.List;

import io.realm.Realm;
import za.ac.wits.elen7046.sirvey.models.realm.QuestionAnswer;
import za.ac.wits.elen7046.sirvey.models.realm.RealmString;
import za.ac.wits.elen7046.sirvey.models.realm.Survey;

public class Translator {

    public void translateRetrofitSurveysToRealmSurveys (List<za.ac.wits.elen7046.sirvey.models.retrofit.Survey> retrofitSurveys,Realm realm) {

        realm.beginTransaction();

        for (za.ac.wits.elen7046.sirvey.models.retrofit.Survey temp : retrofitSurveys) {
            Survey tempSurvey = realm.createObject(Survey.class);

            for (za.ac.wits.elen7046.sirvey.models.retrofit.QuestionAnswer qaTemp : temp.getQuestionAnswers()) {
                QuestionAnswer qa = realm.createObject(QuestionAnswer.class);

                qa.setAnswerType(qaTemp.getAnswerType());
                qa.setAnswer(qaTemp.getAnswer());
                qa.setAnswerType(qaTemp.getAnswerType());
                qa.setMongoId(qaTemp.getId());
                qa.setQuestion(qaTemp.getQuestion());


                for ( String tempString : qaTemp.getAnswerOptions()) {
                    RealmString string = realm.createObject(RealmString.class);

                    string.setName(tempString);

                    qa.getAnswerOptions().add(string);
                }

                tempSurvey.getQuestionAnswers().add(qa);
            }



            tempSurvey.setName(temp.getName());
            tempSurvey.setMongoId(temp.getId());
        }
        realm.commitTransaction();
       realm.close();
    }

//    public RealmList<QuestionAnswer> translateRetrofitQAsToRealmQAs (List<za.ac.wits.elen7046.sirvey.models.retrofit.QuestionAnswer> retrofitQAs, Realm realm) {
//        RealmList<QuestionAnswer> realmQAs= new RealmList<>();
//        realm.beginTransaction();
//        for (za.ac.wits.elen7046.sirvey.models.retrofit.QuestionAnswer temp : retrofitQAs) {
//            QuestionAnswer qa = realm.createObject(QuestionAnswer.class);
//
//            qa.setAnswerType(temp.getAnswerType());
//            qa.setAnswer(temp.getAnswer());
//            qa.setAnswerType(temp.getAnswerType());
//            qa.setMongoId(temp.getId());
//            qa.setQuestion(temp.getQuestion());
//            qa.setAnswerOptions(temp.getAnswerOptions());
//
//            realmQAs.add(qa);
//        }
//
//        return realmQAs;
//    }
}

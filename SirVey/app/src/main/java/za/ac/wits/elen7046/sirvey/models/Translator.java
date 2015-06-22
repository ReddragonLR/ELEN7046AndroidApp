package za.ac.wits.elen7046.sirvey.models;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import za.ac.wits.elen7046.sirvey.models.realm.CompletedSurvey;
import za.ac.wits.elen7046.sirvey.models.realm.QuestionAnswer;
import za.ac.wits.elen7046.sirvey.models.realm.RealmString;
import za.ac.wits.elen7046.sirvey.models.realm.Survey;
import za.ac.wits.elen7046.sirvey.models.retrofit.CompletedQuestionAnswer;

public class Translator {

    private static final String TRANSLATOR = "TRANSLATOR" ;

    public void translateRetrofitSurveysToRealmSurveys (List<za.ac.wits.elen7046.sirvey.models.retrofit.Survey> retrofitSurveys,Realm realm) {
        realm.beginTransaction();

        for (za.ac.wits.elen7046.sirvey.models.retrofit.Survey temp : retrofitSurveys) {
            Survey tempSurvey = realm.createObject(Survey.class);

            for (za.ac.wits.elen7046.sirvey.models.retrofit.QuestionAnswer qaTemp : temp.getQuestionAnswers()) {
                QuestionAnswer qa = realm.createObject(QuestionAnswer.class);

                qa.setAnswerType(qaTemp.getAnswerType());
                qa.setAnswer(qaTemp.getAnswer());
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

        // Implicit read transactions allow you to access your objects
        String status = "\nNumber of Surveys: " + realm.allObjects(Survey.class).size();
        Log.wtf(TRANSLATOR,status);
    }

public List<za.ac.wits.elen7046.sirvey.models.retrofit.CompletedSurvey> translateRealmQCompletedSurveysToRetrofitCompletedSurvey (RealmResults<CompletedSurvey> data) {
        ArrayList<za.ac.wits.elen7046.sirvey.models.retrofit.CompletedSurvey> completedSurveys = new ArrayList<>();
        for (CompletedSurvey surveyTemp : data) {

            za.ac.wits.elen7046.sirvey.models.retrofit.CompletedSurvey survey= new za.ac.wits.elen7046.sirvey.models.retrofit.CompletedSurvey();

            List<za.ac.wits.elen7046.sirvey.models.retrofit.CompletedQuestionAnswer> cQAList = new ArrayList<>();


            for (za.ac.wits.elen7046.sirvey.models.realm.CompletedQuestionAnswer temp : surveyTemp.getCompletedQuestionAnswers()) {
                CompletedQuestionAnswer qa = new CompletedQuestionAnswer();

                qa.setAnswerType(temp.getAnswerType());
                qa.setAnswer(temp.getAnswer());
                qa.setQuestion(temp.getQuestion());
                cQAList.add(qa);
            }

            survey.setSurveyName(surveyTemp.getSurveyName());
            survey.setSurveySupervisor(surveyTemp.getSurveySupervisor());
            survey.setSurveyTaker(surveyTemp.getSurveyTaker());
            survey.setCompletedQuestionAnswers(cQAList);
            completedSurveys.add(survey);
        }
        return completedSurveys;
    }
}

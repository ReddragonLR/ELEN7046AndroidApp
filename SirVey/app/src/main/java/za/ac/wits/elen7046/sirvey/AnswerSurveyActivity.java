package za.ac.wits.elen7046.sirvey;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import za.ac.wits.elen7046.sirvey.models.realm.CompletedQuestionAnswer;
import za.ac.wits.elen7046.sirvey.models.realm.CompletedSurvey;
import za.ac.wits.elen7046.sirvey.models.realm.QuestionAnswer;
import za.ac.wits.elen7046.sirvey.models.realm.RealmString;
import za.ac.wits.elen7046.sirvey.models.realm.Survey;


public class AnswerSurveyActivity extends ActionBarActivity {

    private static final String TAG = AnswerSurveyActivity.class.getName();
    private Toolbar toolbar;
    private String surveyName;
    private ArrayList<Integer> answerIDs;
    private Realm realm;
    private LinearLayout QALayout;
    private RealmResults<Survey> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_survey);
        answerIDs = new ArrayList<>();
        // Open the default realm ones for the UI thread.
        realm = Realm.getInstance(this);


        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                surveyName= null;
            } else {
                surveyName= extras.getString("SURVEY_NAME");
            }
        } else {
            surveyName= (String) savedInstanceState.getSerializable("SURVEY_NAME");
        }

            TextView title = (TextView)findViewById(R.id.titleQA);
            title.setText(surveyName);

            results = realm.where(Survey.class).equalTo("name", surveyName).findAll();

        QALayout= (LinearLayout)findViewById(R.id.QALayout);

//
//        for (int i = 0 ; i <30 ; i++){
//            TextView label = new TextView(this);
//
//            label.setText("Hello");
//
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//            params.setMargins(0, 30, 0, 0);
//            params.gravity =
//                    Gravity.CENTER;
//            label.setLayoutParams(params);
//            label.setTextSize(20);
//
//
//            QALayout.addView(label);
//      }
//        TextView label = new TextView(this);
//
//        label.setText("END");
//        label.setTextSize(20);
//        label.setGravity(Gravity.CENTER_HORIZONTAL);
//        QALayout.addView(label);
        for (Survey survey: results ) {

            for (int i = 0 ; i < survey.getQuestionAnswers().size() ;i++ ) {

                if(survey.getQuestionAnswers().get(i).getAnswerType().equals("Text")){
                    addTextAnswerTypeToView(survey.getQuestionAnswers().get(i),i);
                }

                if(survey.getQuestionAnswers().get(i).getAnswerType().equals("Dropdown")){
                    addDropDownAnswerTypeToView(survey.getQuestionAnswers().get(i), i);
                }

                if(survey.getQuestionAnswers().get(i).getAnswerType().equals("YesNo")) {
                    addYesNoAnswerTypeToView(survey.getQuestionAnswers().get(i), i);
                }
            }
        }




        //ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.CustomBorderlessButtonStyle);
        Button submitButton = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 30, 0, 10);
            params.gravity = Gravity.CENTER;
        submitButton.setLayoutParams(params);
        submitButton.setTextSize(20);

        submitButton.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
        submitButton.setTextColor(getResources().getColor(R.color.whiteColor));
        submitButton.setText("Save Survey Response");
        submitButton.setOnClickListener(submitSurveyButtonPressed());

        QALayout.addView(submitButton);

        Log.wtf(TAG, "BR");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTextAnswerTypeToView (QuestionAnswer QA,int i) {
        TextView questionLabel = new TextView(this);
        questionLabel.setText(i + 1 + ". " + QA.getQuestion());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 15, 10, 0);
            params.gravity =
                    Gravity.CENTER;
        questionLabel.setLayoutParams(params);
        questionLabel.setTextSize(20);


            QALayout.addView(questionLabel);

        EditText answer = new EditText(this);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        addID(i);
        answer.setId(getIDAtIndex(i));

        // setMargins(int left, int top, int right, int bottom)
        params.setMargins(10, 15, 10, 0);
        answer.setLayoutParams(params);
        answer.setTextSize(20);
        QALayout.addView(answer);
    }

    private void addDropDownAnswerTypeToView (QuestionAnswer QA,int i) {
        TextView questionLabel = new TextView(this);
        questionLabel.setText(i + 1 + ". " + QA.getQuestion());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 15, 10, 0);
        params.gravity =
                Gravity.CENTER;
        questionLabel.setLayoutParams(params);
        questionLabel.setTextSize(20);


        QALayout.addView(questionLabel);


        RadioGroup radioGroup = new RadioGroup(this);
        addID(i);
        radioGroup.setId(getIDAtIndex(i));

        for (RealmString answerOption : QA.getAnswerOptions()) {
            RadioButton radioButton = new RadioButton(this);
            String string = answerOption.getName();
            radioButton.setText(string);

            radioGroup.addView(radioButton);
        }
        QALayout.addView(radioGroup);
    }

    private void addYesNoAnswerTypeToView (QuestionAnswer QA,int i) {
        TextView questionLabel = new TextView(this);
        questionLabel.setText(i + 1 + ". " + QA.getQuestion());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 15, 10, 0);
        params.gravity =
                Gravity.CENTER;
        questionLabel.setLayoutParams(params);
        questionLabel.setTextSize(20);

        QALayout.addView(questionLabel);

        RadioGroup radioGroup = new RadioGroup(this);
        addID(i);
        radioGroup.setId(getIDAtIndex(i));
        RadioButton yesRadioButton = new RadioButton(this);
        yesRadioButton.setText("Yes");

        RadioButton noRadioButton = new RadioButton(this);
        noRadioButton.setText("No");


        radioGroup.addView(noRadioButton);
        radioGroup.addView(yesRadioButton);

        QALayout.addView(radioGroup);
    }


    View.OnClickListener submitSurveyButtonPressed()  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                //Realm r = Realm.getInstance(getApplicationContext());
                realm.beginTransaction();

                Survey survey = results.get(0);
                CompletedSurvey completedSurvey = realm.createObject(CompletedSurvey.class);
                completedSurvey.setSurveyTaker("Anonymous");
                completedSurvey.setSurveySupervisor("Android");
                completedSurvey.setSurveyName(survey.getName());
                Log.wtf(TAG, "Butt pressed");

                boolean isSurveyValid = true;
                for (int i = 0; i < survey.getQuestionAnswers().size(); i++) {

                    boolean isValid;
                    if (survey.getQuestionAnswers().get(i).getAnswerType().equals("Text")) {
                        isValid = validateTextAnswer(i);
                        if (isValid) {
                            Log.wtf(TAG, "Valid Answer of: " + retrieveTextAnswer(i));
                            CompletedQuestionAnswer questionAnswer = realm.createObject(CompletedQuestionAnswer.class);
                            questionAnswer.setAnswer(retrieveTextAnswer(i));
                            questionAnswer.setAnswerType("Text");
                            questionAnswer.setQuestion(survey.getQuestionAnswers().get(i).getQuestion());
                            completedSurvey.getCompletedQuestionAnswers().add(questionAnswer);
                        } else {
                            Log.wtf(TAG, "InValid Answer of: " + i);
                            int questionNo = i + 1;
                            String message = "Invalid answer for Question: " + questionNo;
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            isSurveyValid = false;
                            break;
                        }
                    }

                    if (survey.getQuestionAnswers().get(i).getAnswerType().equals("YesNo")
                            || survey.getQuestionAnswers().get(i).getAnswerType().equals("Dropdown")) {
                        isValid = validateRadioGroup(i);
                        if (isValid) {
                            Log.wtf(TAG, "Valid Answer of: " + retrieveSelectedAnswer(i));
                            if (survey.getQuestionAnswers().get(i).getAnswerType().equals("Dropdown")) {
                                CompletedQuestionAnswer questionAnswer = realm.createObject(CompletedQuestionAnswer.class);
                                questionAnswer.setAnswer(retrieveSelectedAnswer(i));
                                questionAnswer.setAnswerType("Dropdown");
                                questionAnswer.setQuestion(survey.getQuestionAnswers().get(i).getQuestion());
                                completedSurvey.getCompletedQuestionAnswers().add(questionAnswer);
                            }

                            if (survey.getQuestionAnswers().get(i).getAnswerType().equals("YesNo")) {
                                CompletedQuestionAnswer questionAnswer = realm.createObject(CompletedQuestionAnswer.class);
                                questionAnswer.setAnswer(retrieveSelectedAnswer(i));
                                questionAnswer.setAnswerType("YesNo");
                                questionAnswer.setQuestion(survey.getQuestionAnswers().get(i).getQuestion());
                                completedSurvey.getCompletedQuestionAnswers().add(questionAnswer);
                            }

                        } else {
                            int questionNo = i + 1;
                            Log.wtf(TAG, "InValid Answer of: " + i);
                            String message = "Invalid answer for Question: " + questionNo;
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            isSurveyValid = false;
                            break;
                        }
                    }
                }
                if (isSurveyValid) {
                    Log.wtf(TAG, "Submit Survey Ready to commit to database");

                    realm.commitTransaction();
                    Toast.makeText(getApplicationContext(), "Survey captured successfully!", Toast.LENGTH_SHORT).show();
                finish();


                }
                else  {
                    realm.cancelTransaction();
                }
            }
        };
    }

    private String retrieveSelectedAnswer(int index) {
        int ID = answerIDs.get(index);
        RadioGroup rg = (RadioGroup)findViewById(ID);

        if(rg.getCheckedRadioButtonId()!= -1){
            int id= rg.getCheckedRadioButtonId();
            View radioButton = rg.findViewById(id);
            int radioId = rg.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rg.getChildAt(radioId);
            String selection =  btn.getText().toString();


            return selection;
        }
        String shouldNeverHappen = "";
        return shouldNeverHappen;
    }

    private Boolean validateTextAnswer(int index) {
        int ID = answerIDs.get(index);
        EditText editText = (EditText)findViewById(ID);

        if(editText.getText().toString().equals("")) {
            return false;
        }
        else
        {
          return true;
        }
    }

    private String retrieveTextAnswer(int index) {
        int ID = answerIDs.get(index);
        EditText editText = (EditText)findViewById(ID);
        String data = editText.getText().toString();
        return data;
    }

    private Boolean validateRadioGroup(int index){
        int ID = answerIDs.get(index);
        RadioGroup rg = (RadioGroup)findViewById(ID);
        if(rg.getCheckedRadioButtonId() == -1) {
            return false;
        } else {
            return true;
        }
    }

    private void addID (int i) {
        answerIDs.add(i+500);
    }

    private int getIDAtIndex(int index) {
        return answerIDs.get(index);
    }
}

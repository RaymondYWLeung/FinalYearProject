package comps456f.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Examination1 extends AppCompatActivity implements View.OnClickListener{

    int score = 0;
    Boolean check[] = new Boolean[2];
    private Context context;
    private String longQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination1);

        TextView longQuestionView = (TextView)findViewById(R.id.longQuestion);
        context = getApplicationContext();
        ApiHandler getQuestion = new ApiHandler();
        longQuestion = getQuestion.getRequest(context, "http://192.168.220.23:3000/api/testing");
        Log.e("exam 1", longQuestion);
        longQuestionView.setText(longQuestion);

        Button submit_but = (Button)findViewById(R.id.exam1_submit_button);
        submit_but.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {

        RadioGroup question1 = (RadioGroup) findViewById(R.id.exam1_q1rg);
        String answer1 = ((RadioButton) findViewById(question1.getCheckedRadioButtonId())).getText().toString();

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.exam1_q1a:
                if (checked)
                    check[0] = Boolean.FALSE;
                break;
            case R.id.exam1_q1b:
                if (checked)
                    check[0] = Boolean.FALSE;
                break;
            case R.id.exam1_q1c:
                if (checked)
                    check[0] = Boolean.TRUE;
                break;
        }
        switch(view.getId()) {
            case R.id.exam1_q2a:
                if (checked)
                    check[1] = Boolean.TRUE;
                break;
            case R.id.exam1_q2b:
                if (checked)
                    check[1] = Boolean.FALSE;
                break;
            case R.id.exam1_q2c:
                if (checked)
                    check[1] = Boolean.FALSE;
                break;
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        score = 0;

        if(id == R.id.exam1_submit_button){
            for(Boolean question:check){
                if(question == Boolean.TRUE){
                    score += 1;
                }
            }
            Log.d("Q1", check[0] + "");
            Log.d("Q2", check[1] + "");
            Toast.makeText(this, "Your score is : " + score, Toast.LENGTH_SHORT).show();
        }
        for(Boolean question:check){
            question = Boolean.FALSE;
        }

    }
}

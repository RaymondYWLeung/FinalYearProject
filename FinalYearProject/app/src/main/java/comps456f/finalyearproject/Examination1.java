package comps456f.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.lang.Math;

public class Examination1 extends AppCompatActivity implements View.OnClickListener{

    int score = 0;
    Boolean check[] = new Boolean[2];
    String questionTitle = "";
    String questionNumber = "";
    String questionAnswer = "";
    String questionType = "";
    String questionContentForFib = "";
    String url ="http://192.168.220.15:3000/api/testing/Exam01";
    String url2 = "http://192.168.220.15:3000/api/insert";
    ArrayList<String> questionChoicesForMc = new ArrayList<String>();

    //View for fib
    ArrayList<EditText> editText = new ArrayList<EditText>();
    ArrayList<String> fibAns = new ArrayList<String>();

    //View for mc
    ArrayList<TextView> mcTextView = new ArrayList<TextView>();
    ArrayList<RadioGroup> rg = new ArrayList<RadioGroup>();
    ArrayList<RadioButton> rb = new ArrayList<RadioButton>();
    ArrayList<String> mcAns = new ArrayList<String>();

    //Question
    ArrayList<FillInTheBlanks> fib = new ArrayList<FillInTheBlanks>();
    ArrayList<MultipleChoice> mc = new ArrayList<MultipleChoice>();

    //Random question number
    //int q1 = (int)(Math.random()*10)+1;
    int q1 = 1;
    int q2 = 7;
    int q3 = 8;
    //int q2 = (int)(Math.random()*10)+1;
    //int q3 = (int)(Math.random()*10)+1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination1);

        /*Reference
        TextView testing = new TextView(this);
        testing.setText("Try");
        LinearLayout exam = (LinearLayout)findViewById(R.id.exam01);
        exam.addView(testing);

        Button button =  new Button(this);
        button.setText("OK");
        exam.addView(button);
        */

        RequestQueue queue = Volley.newRequestQueue(this);

        final LinearLayout exam = (LinearLayout)findViewById(R.id.exam01);

        final Context appContext = getApplicationContext();


        //HTTP GET ok
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {

                try {


                    for (int i=0;i<response.length();i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        questionNumber = jsonObject.getString("QuestionNumber");
                        questionType = jsonObject.getString("QuestionType");
                        questionTitle = jsonObject.getString("QuestionTitle");
                        questionTitle = questionTitle.replaceAll("/n","\n").replaceAll("/t", "\t").replaceAll("\\\\", " ");
                        questionAnswer = jsonObject.getString("Answer");

                        if(Integer.toString(q1).equals(questionNumber) || Integer.toString(q2).equals(questionNumber) || Integer.toString(q3).equals(questionNumber)){
                            if(questionType.equals("FillIn")){
                                Log.e("testing",new FillInTheBlanks(questionNumber, questionType, questionTitle, questionAnswer).getQuestionTitle());
                                fib.add(new FillInTheBlanks(questionNumber, questionType, questionTitle, questionAnswer));

                            }else if(questionType.equals("MC")){
                                JSONArray contentArray = jsonObject.getJSONArray("Choices");
                                if(contentArray.length() != 0){
                                    for(int j=0; i<contentArray.length(); i++){
                                        questionChoicesForMc.add(contentArray.getString(j));
                                    }
                                }
                                Log.e("testing",new MultipleChoice(questionNumber, questionType, questionTitle, questionChoicesForMc, questionAnswer).getQuestionTitle());
                                mc.add(new MultipleChoice(questionNumber, questionType, questionTitle, questionChoicesForMc, questionAnswer));
                            }
                        }
                    }

                    //Fib
                    Log.e("fibSize", fib.size()+"");
                    for(int i=0; i<fib.size(); i++){
                        TextView view = new TextView(appContext);
                        EditText edit= new EditText(appContext);

                        //textview
                        view.setText(fib.get(i).getQuestionTitle());
                        view.setTextColor(Color.BLACK);
                        view.setTextSize(14);
                        //editText
                        
                        edit.setBackgroundColor(Color.GRAY);
                        editText.add(edit);
                        fibAns.add(fib.get(i).getAnswer());
                        exam.addView(view);
                        exam.addView(edit);
                    }

                    //Mc
                    Log.e("mcSize", mc.size()+"");
                    for(int i=0; i<mc.size(); i++){
                        TextView view = new TextView(appContext);
                        view.setText(fib.get(i).getQuestionTitle());
                        view.setTextColor(Color.BLACK);
                        view.setTextSize(14);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("user",e.getMessage());
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("CheckFAIL", error.toString());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

        /*Log.e("size", fib.size()+"");

        for(int i=0; i<fib.size(); i++){
            TextView view = new TextView(this);
            view.setText(fib.get(i).getQuestionTitle());
            exam.addView(view);
        }*/

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
        /*Post Request Example
        context = this.getApplicationContext();
        ApiHandler handler = new ApiHandler();
        handler.postRequest(context, url2);
        */

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

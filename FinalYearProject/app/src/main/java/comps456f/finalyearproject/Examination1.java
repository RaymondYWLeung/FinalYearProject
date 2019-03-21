package comps456f.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
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
    int no;
    Boolean check[] = new Boolean[2];
    String questionTitle = "";
    String questionNumber = "";
    String questionAnswer = "";
    String questionType = "";
    String questionContentForFib = "";
    String url ="http://192.168.221.53:3000/api/examquestion/Exam01";
    String url2 = "http://192.168.220.15:3000/api/insert";

    //View for fib
    ArrayList<String> fibAns = new ArrayList<>();

    //View for mc
    ArrayList<String> mcAns = new ArrayList<>();
    ArrayList<RadioGroup> mcAnsGroup = new ArrayList<>();

    //Question
    ArrayList<FillInTheBlanks> fib = new ArrayList<>();
    ArrayList<MultipleChoice> mc = new ArrayList<>();

    //Result score
    boolean resultForMc[];
    //boolean resultForFib[];

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


                    for (int questionNo=0;questionNo<response.length();questionNo++) {
                        JSONObject jsonObject = response.getJSONObject(questionNo);
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
                                Log.e("length", contentArray.length()+"");
                                ArrayList<String> questionChoicesForMc = new ArrayList<String>();
                                if(contentArray.length() != 0){
                                    for(int questionChoice=0; questionChoice<contentArray.length(); questionChoice++){

                                        questionChoicesForMc.add(contentArray.getString(questionChoice));
                                        Log.e("choice", contentArray.getString(questionChoice));
                                    }
                                    //Log.e("size", mc.get(i).getChoices().size()+"");
                                }
                                Log.e("choiceSize",new MultipleChoice(questionNumber, questionType, questionTitle, questionChoicesForMc, questionAnswer).getChoices().size()+"");
                                mc.add(new MultipleChoice(questionNumber, questionType, questionTitle, questionChoicesForMc, questionAnswer));
                            }
                        }
                    }
                    Log.e("AIOOB", fib.size()+"");
                    resultForMc = new boolean[mc.size()];
                    for(int mcCount=0; mcCount<resultForMc.length; mcCount++){
                        resultForMc[mcCount] = Boolean.FALSE;
                    }

                    /*resultForFib = new boolean[fib.size()];
                    for(int fibCount=0; fibCount<resultForFib.length; fibCount++){
                        resultForFib[fibCount] = Boolean.FALSE;
                    }*/


                    //Fib
                    int fibIdCount = 200;
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
                        edit.setId(fibIdCount);
                        Log.e("fibCount", fibIdCount+"");

                        fibAns.add(fib.get(i).getAnswer());
                        exam.addView(view);
                        exam.addView(edit);
                        fibIdCount++;
                    }

                    //Mc
                    Log.e("mcSize", mc.size()+"");
                    int mcIdCount = 100;
                    for(int i=0; i<mc.size(); i++){
                        TextView view = new TextView(appContext);
                        RadioGroup rg = new RadioGroup(appContext);

                        view.setText(mc.get(i).getQuestionTitle());
                        view.setTextColor(Color.BLACK);
                        view.setTextSize(14);

                        for(int j=0; j<mc.get(i).getChoices().size(); j++){
                            mcIdCount = mcIdCount+j;

                            RadioButton rb = new RadioButton(appContext);
                            rb.setText(mc.get(i).getChoices().get(j));
                            rb.setTextColor(Color.BLACK);
                            rb.setId(mcIdCount);
                            rb.setTag("q"+i+"choice"+j);
                            rb.setButtonTintList(ColorStateList.valueOf(Color.BLACK));
                            rb.setTextSize(14);
                            rg.addView(rb);

                        }
                        mcIdCount = 100+10;
                        mcAns.add(mc.get(i).getAnswer());
                        rg.setId(i);
                        no = i;
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int z) {
                                RadioButton select = (RadioButton)findViewById(z);
                                int selectedId = radioGroup.getId();
                                for(int j=0; j<mcAns.size(); j++){

                                    //Log.e("Onclick Loop",mcAns.get(j) + " : " + select.getText());
                                    if(mcAns.get(j).equals(select.getText())){
                                        resultForMc[selectedId] = Boolean.TRUE;
                                        break;
                                    }else if(!mcAns.get(j).equals(select.getText())){
                                        resultForMc[selectedId] = Boolean.FALSE;
                                    }
                                }
                            }
                        });
                        //mcAnsGroup.add(rg);
                        exam.addView(view);
                        exam.addView(rg);
                    }
                    //Check answer
                    //RadioGroup mcAnsGroup = (RadioGroup)findViewById(0);
                    //Log.e("Selection", ((RadioButton) findViewById(mcAnsGroup.getCheckedRadioButtonId())).getText().toString());



                    //Submit button
                    int submitButId=999;
                    Button submitBut = new Button(appContext);
                    submitBut.setText("Submit");
                    exam.addView(submitBut);
                    submitBut.setId(submitButId);
                    submitBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            score = 0;
                            for(int mcArray=0; mcArray<resultForMc.length; mcArray++){
                                if(resultForMc[mcArray] == Boolean.TRUE){
                                    score++;
                                }
                            }
                            for(int fibArray=0; fibArray<fibAns.size(); fibArray++){
                                int fibId = fibArray+200;
                                EditText edit = (EditText)findViewById(fibId);
                                Log.e("fillAns",fibAns.get(fibArray));
                                Log.e("editText", edit.getText().toString());
                                if(fibAns.get(fibArray).equals(edit.getText().toString())){
                                    score++;
                                }
                            }
                            int id = view.getId();
                            if(id == 999){
                                Toast.makeText(appContext, "Your score is : " + score, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


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
            //Toast.makeText(this, "Your score is : " + score, Toast.LENGTH_SHORT).show();
        }
        for(Boolean question:check){
            question = Boolean.FALSE;
        }

    }
}

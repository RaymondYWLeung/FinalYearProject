package comps456f.finalyearproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

public class Examination2 extends AppCompatActivity {

    //common variable
    int score = 0;
    int no;
    String questionTitle = "";
    String questionNumber = "";
    String questionAnswer = "";
    String questionType = "";
    //String url = "https://raymondsfypapi.herokuapp.com/api/examquestion/Exam01";
    String url = "http://192.168.240.17:3000/api/templateQuestion/Exam02";

    //View for fib
    ArrayList<String> fibAns = new ArrayList<>();

    //View for mc
    ArrayList<String> mcAns = new ArrayList<>();

    //Question
    ArrayList<FillInTheBlanks> fib = new ArrayList<>();
    ArrayList<MultipleChoice> mc = new ArrayList<>();

    //Result score
    boolean resultForMc[];

    //Random question number
    ArrayList<Integer> questionNoArrayList = new ArrayList<>();

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination1);

        while(questionNoArrayList.size()<=15){
            int randomQuestionNo = (int) (Math.random() * 19) + 1;
            if(!questionNoArrayList.contains(randomQuestionNo)){
                questionNoArrayList.add(randomQuestionNo);
            }
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        final LinearLayout exam = (LinearLayout) findViewById(R.id.exam01);

        final Context appContext = getApplicationContext();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Question ....");
        progressDialog.show();

        //HTTP GET ok
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {

                try {


                    for (int questionNo = 0; questionNo < response.length(); questionNo++) {
                        JSONObject jsonObject = response.getJSONObject(questionNo);
                        questionNumber = jsonObject.getString("QuestionNumber");
                        questionType = jsonObject.getString("QuestionType");
                        questionTitle = jsonObject.getString("QuestionTitle");
                        questionTitle = questionTitle.replaceAll("/n", "\n").replaceAll("/t", "\t").replaceAll("\\\\", " ");
                        questionAnswer = jsonObject.getString("Answer");

                        for(int i=0; i<questionNoArrayList.size(); i++) {
                            if (questionNoArrayList.get(i).toString().equals(questionNumber)) {
                                if (questionType.equals("FillIn")) {
                                    fib.add(new FillInTheBlanks(questionNumber, questionType, questionTitle, questionAnswer));

                                } else if (questionType.equals("MC")) {
                                    JSONArray contentArray = jsonObject.getJSONArray("Choices");
                                    ArrayList<String> questionChoicesForMc = new ArrayList<>();
                                    if (contentArray.length() != 0) {
                                        for (int questionChoice = 0; questionChoice < contentArray.length(); questionChoice++) {

                                            questionChoicesForMc.add(contentArray.getString(questionChoice));
                                        }
                                    }
                                    mc.add(new MultipleChoice(questionNumber, questionType, questionTitle, questionChoicesForMc, questionAnswer));
                                }
                            }
                        }
                    }
                    resultForMc = new boolean[mc.size()];
                    for (int mcCount = 0; mcCount < resultForMc.length; mcCount++) {
                        resultForMc[mcCount] = Boolean.FALSE;
                    }

                    progressDialog.dismiss();

                    //Text View of the title
                    TextView br = new TextView(appContext);
                    br.setText("");
                    br.setTextSize(20);
                    exam.addView(br);
                    TextView title = new TextView(appContext);
                    title.setText("Exam 2");
                    title.setGravity(Gravity.TOP | Gravity.CENTER);
                    title.setTextColor(Color.BLACK);
                    title.setTextSize(42);
                    exam.addView(title);

                    //Fib
                    int fibIdCount = 500;
                    for (int i = 0; i < fib.size(); i++) {
                        TextView view = new TextView(appContext);
                        EditText edit = new EditText(appContext);
                        TextView newline = new TextView(appContext);

                        //textview
                        view.setText(fib.get(i).getQuestionTitle());
                        view.setTextColor(Color.BLACK);
                        view.setTextSize(15);
                        view.setPadding(100,0,5,0);

                        //editText
                        edit.setBackgroundColor(Color.GRAY);
                        edit.setId(fibIdCount);

                        //newline
                        newline.setTextSize(20);
                        newline.setText("");

                        fibAns.add(fib.get(i).getAnswer());
                        exam.addView(view);
                        exam.addView(edit);
                        exam.addView(newline);
                        fibIdCount++;
                    }

                    //MC Question
                    int mcIdCount = 100;
                    for (int i = 0; i < mc.size(); i++) {
                        TextView view = new TextView(appContext);
                        RadioGroup rg = new RadioGroup(appContext);
                        TextView newline = new TextView(appContext);

                        //View
                        view.setText(mc.get(i).getQuestionTitle());
                        view.setTextColor(Color.BLACK);
                        view.setTextSize(15);
                        view.setPadding(100,0,5,0);

                        //newline
                        newline.setTextSize(20);
                        newline.setText("");

                        //Create radio button and radio group
                        for (int j = 0; j < mc.get(i).getChoices().size(); j++) {
                            mcIdCount = mcIdCount + j;
                            RadioButton rb = new RadioButton(appContext);
                            rb.setText(mc.get(i).getChoices().get(j));
                            rb.setTextColor(Color.BLACK);
                            rb.setId(mcIdCount);
                            rb.setTag("q" + i + "choice" + j);
                            rb.setButtonTintList(ColorStateList.valueOf(Color.BLACK));
                            rb.setTextSize(14);
                            //rb.setPadding(100,0,5,0);
                            rg.addView(rb);
                            rg.setPadding(100,0,5,0);
                        }
                        mcIdCount = 100 + 10;
                        mcAns.add(mc.get(i).getAnswer());
                        rg.setId(i);
                        no = i;

                        //Radio Button Listener
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int z) {
                                RadioButton select = (RadioButton) findViewById(z);
                                int selectedId = radioGroup.getId();
                                for (int j = 0; j < mcAns.size(); j++) {

                                    //check correct
                                    if (mcAns.get(j).equals(select.getText())) {
                                        resultForMc[selectedId] = Boolean.TRUE;
                                        break;

                                        //check incorrect
                                    } else if (!mcAns.get(j).equals(select.getText())) {
                                        resultForMc[selectedId] = Boolean.FALSE;
                                    }
                                }
                            }
                        });
                        exam.addView(view);
                        exam.addView(rg);
                        exam.addView(newline);
                    }

                    //submit button
                    int submitButId = 999;
                    Button submitBut = new Button(appContext);
                    submitBut.setText("Submit");
                    exam.addView(submitBut);
                    submitBut.setId(submitButId);

                    //grade of score
                    final int excellent = (fib.size() + mcAns.size()) * 2 / 3;
                    final int pass = (fib.size() + mcAns.size()) * 4 / 10;

                    submitBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            score = 0;

                            //check mcArray
                            for (int mcArray = 0; mcArray < resultForMc.length; mcArray++) {
                                if (resultForMc[mcArray] == Boolean.TRUE) {
                                    score++;
                                }
                            }

                            //check fillInTheBlanks answer
                            for (int fibArray = 0; fibArray < fibAns.size(); fibArray++) {
                                int fibId = fibArray + 500;
                                EditText edit = (EditText) findViewById(fibId);
                                if (fibAns.get(fibArray).equals(edit.getText().toString())) {
                                    score++;
                                }
                            }

                            int id = view.getId();
                            if (id == 999) {
                                Toast toast;
                                if(score >= excellent){
                                    toast = Toast.makeText(appContext, "Well Done, Your score is : " + score + ".\nYou look understand it well", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.show();
                                }else if(score < excellent && score >= pass){
                                    toast = Toast.makeText(appContext, "Well, Your score is : " + score + ".\nYou should study more and make sure you understand the concept", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.show();
                                }else if(score < pass){
                                    toast = Toast.makeText(appContext, "Your score is : " + score + ".\nYou must study well. Your concept is not good at all", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.show();
                                }

                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("user", e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CheckFAIL", error.toString());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }
}

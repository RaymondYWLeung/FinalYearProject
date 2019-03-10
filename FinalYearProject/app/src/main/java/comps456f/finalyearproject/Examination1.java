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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Examination1 extends AppCompatActivity implements View.OnClickListener{

    int score = 0;
    Boolean check[] = new Boolean[2];
    String data = "";
    String url ="http://192.168.220.23:3000/api/testing";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination1);

        final TextView longQuestionView = (TextView)findViewById(R.id.longQuestion);
        RequestQueue queue = Volley.newRequestQueue(this);

        //HTTP GET ok
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {

                //Print all value
                //Log.e("CheckOK", response.toString());


                try {
                    //Print all "Name" value

                    for (int i=0;i<response.length();i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        //data=jsonObject.getString("Content");
                        data = jsonObject.getString("Content");
                        data = data.replaceAll("/n","\n").replaceAll("\\\\", " ");
                        longQuestionView.setText(data);
                        Log.e("apihandler", data);
                    }

                    //Print individual "Name" value
                    //JSONObject jsonObject = response.getJSONObject(0);
                    //data=jsonObject.getString("Name");
                    //Log.e("Name", data);

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
            Toast.makeText(this, "Your score is : " + score, Toast.LENGTH_SHORT).show();
        }
        for(Boolean question:check){
            question = Boolean.FALSE;
        }

    }
}

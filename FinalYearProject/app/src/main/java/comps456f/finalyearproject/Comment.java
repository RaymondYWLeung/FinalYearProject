package comps456f.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Comment extends AppCompatActivity implements View.OnClickListener{

    private String nameOfUser;
    private String idOfUser;
    private String title;
    private String content;
    private Date date;
    private EditText editViewTitle;
    private EditText editViewContent;
    private Button submit;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        editViewContent = (EditText)findViewById(R.id.editText_comment);
        submit = (Button)findViewById(R.id.post_sumbit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        JSONObject sumbitField = new JSONObject();;
        final Context appContext = getApplicationContext();

        idOfUser = getSharedPreferences("user", MODE_PRIVATE).getString("ID", "NULL");;
        content = editViewContent.getText().toString();

        int id = view.getId();

        if(id == R.id.post_sumbit){

            Log.e("Checking", nameOfUser);

            RequestQueue queue = Volley.newRequestQueue(appContext);
            try {
                sumbitField.put("userId", idOfUser);
                sumbitField.put("content", content);
            } catch (JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }

            Log.e("Checking2", nameOfUser);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, sumbitField, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if(response.optString("status").equals("ok")){
                        Intent Intent = new Intent(Comment.this,ForumFrontPage.class);
                        finish();
                        startActivity( Intent);
                    }else{
                        Intent Intent = new Intent(Comment.this,Comment.class);
                        finish();
                        startActivity( Intent);
                    }
                    Log.e("OnResponse",response.optString("out"));

                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.e("Fail", error.toString());
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);

        }

    }
}

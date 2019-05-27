package comps456f.finalyearproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class SoloPost extends AppCompatActivity {

    private String objectId = "";
    private String postTitle = "";
    private String userId= "";
    private String postDate = "";
    private String postContent = "";
    private String url;
    //private int counter;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsolopost);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        RequestQueue queue = Volley.newRequestQueue(this);

        final LinearLayout forumTable = (LinearLayout) findViewById(R.id.soloPost);
        final GridLayout content = (GridLayout) findViewById(R.id.mainContent);

        final Context appContext = getApplicationContext();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Post ....");
        progressDialog.show();

        //counter = Integer.parseInt(url.substring(url.length()-1,url.length()));

        Log.e("url", url);

        //HTTP GET ok
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {

                try {

                        objectId = response.getString("_id");
                        postTitle = response.getString("title");
                        userId = response.getJSONArray("comment").getJSONObject(0).getString("userId");
                        postDate = response.getString("date");
                        postContent = response.getJSONArray("comment").getJSONObject(0).getString("content");



                        String comment[] = new String[response.getJSONArray("comment").length()];
                        String commentUser[] = new String[response.getJSONArray("comment").length()];

                        for(int i=1; i<response.getJSONArray("comment").length(); i++){
                            commentUser[i] = response.getJSONArray("comment").getJSONObject(i).getString("userId");
                            comment[i] = response.getJSONArray("comment").getJSONObject(i).getString("content");
                        }

                        TextView postTitleView = (TextView)findViewById(R.id.postName);
                        postTitleView.setText(postTitle);


                        TextView userNameTd = new TextView(appContext);
                        userNameTd.setText(userId);
                        userNameTd.setTextColor(Color.BLACK);
                        userNameTd.setTextSize(20);
                    forumTable.addView(userNameTd);

                        Space space = new Space(appContext);
                        space.setMinimumWidth(20);
                    forumTable.addView(space);

                        TextView contentTd = new TextView(appContext);
                        contentTd.setText(postContent);
                        contentTd.setTextColor(Color.BLACK);
                        contentTd.setTextSize(20);
                    forumTable.addView(contentTd);

                        int createButtonId = -10;
                        Button createButton = new Button(appContext);
                        createButton.setText("Create Comment");
                        createButton.setTextColor(Color.BLACK);
                        createButton.setTextSize(15);
                        createButton.setId(createButtonId);
                        content.addView(createButton);

                        createButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent Intent = new Intent(SoloPost.this,CreateComment.class);
                                Intent.putExtra("url","https://raymondsfypapi.herokuapp.com/api/viewpost/"+objectId+"/comment");
                                finish();
                                startActivity(Intent);
                            }
                        });


                        for(int i=0; i<commentUser.length; i++){

                            TextView commentUserNameTd = new TextView(appContext);
                            commentUserNameTd.setText(commentUser[i]);
                            commentUserNameTd.setTextColor(Color.BLACK);
                            commentUserNameTd.setTextSize(20);
                            content.addView(commentUserNameTd);

                            Space spaceForComment = new Space(appContext);
                            spaceForComment.setMinimumWidth(20);
                            content.addView(spaceForComment);

                            TextView commentContentTd = new TextView(appContext);
                            commentContentTd.setText(comment[i]);
                            commentContentTd.setTextColor(Color.BLACK);
                            commentContentTd.setTextSize(20);
                            content.addView(commentContentTd);
                        }



                    progressDialog.dismiss();

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

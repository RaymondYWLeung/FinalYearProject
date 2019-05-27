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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ForumFrontPage extends AppCompatActivity {

    private String[] objectId;
    private String[] postTitle;
    private String[] userId;
    private String[] postDate;

    private ProgressDialog progressDialog;

    String url = "https://raymondsfypapi.herokuapp.com/api/viewpost/0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_front_page);

        RequestQueue queue = Volley.newRequestQueue(this);

        final LinearLayout forumTable = (LinearLayout) findViewById(R.id.forum);



        final Context appContext = getApplicationContext();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Post ....");
        progressDialog.show();



        //HTTP GET ok
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {

                try {

                    //forumTable.addView(td);
                    objectId = new String[response.length()];
                    postTitle = new String[response.length()];
                    userId = new String[response.length()];
                    postDate = new String[response.length()];

                    for (int postNo = 0; postNo < response.length(); postNo++) {
                        JSONObject jsonObject = response.getJSONObject(postNo);
                        objectId[postNo] = jsonObject.getString("_id");
                        postTitle[postNo] = jsonObject.getString("title");
                        userId[postNo] = jsonObject.getJSONArray("comment").getJSONObject(0).getString("userId");
                        postDate[postNo] = jsonObject.getString("date");

                        TextView userNameTd = new TextView(appContext);
                        userNameTd.setText(userId[postNo]);
                        userNameTd.setTextColor(Color.BLACK);
                        userNameTd.setTextSize(20);
                        forumTable.addView(userNameTd);

                        Button postTitleTd = new Button(appContext);
                        postTitleTd.setText(postTitle[postNo]);
                        postTitleTd.setTextColor(Color.BLACK);
                        postTitleTd.setTextSize(15);
                        postTitleTd.setId(postNo);
                        postTitleTd.setTag(postNo);
                        //postTitleTd.setOnClickListener(appContext);
                        forumTable.addView(postTitleTd);
                        Log.e("url", objectId[postNo]);
                        postTitleTd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Log.e("Button",view.getTag().toString());
                                Intent Intent = new Intent(ForumFrontPage.this,SoloPost.class);
                                //Log.e("url", objectId[postNo]);
                                Intent.putExtra("url","https://raymondsfypapi.herokuapp.com/api/viewIndpost/"+objectId[Integer.parseInt(view.getTag().toString())]);
                                finish();
                                startActivity(Intent);
                            }
                        });

                        TextView postDateTd = new TextView(appContext);
                        postDateTd.setText(postDate[postNo]);
                        postDateTd.setTextColor(Color.BLACK);
                        postDateTd.setTextSize(20);
                        forumTable.addView(postDateTd);

                    }

                    int createButtonId = -2;
                    Button createButton = new Button(appContext);
                    createButton.setText("Create Post");
                    createButton.setTextColor(Color.BLACK);
                    createButton.setTextSize(15);
                    createButton.setId(createButtonId);
                    forumTable.addView(createButton);

                    createButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent Intent = new Intent(ForumFrontPage.this,CreatePost.class);
                            finish();
                            startActivity( Intent);
                        }
                    });


                    int buttonId = -1;
                    Button nextPage = new Button(appContext);
                    nextPage.setText("Next Page");
                    nextPage.setTextColor(Color.BLACK);
                    nextPage.setTextSize(15);
                    nextPage.setId(buttonId);
                    forumTable.addView(nextPage);

                    nextPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent Intent = new Intent(ForumFrontPage.this,NextPageBackPage.class);
                            Intent.putExtra("url","https://raymondsfypapi.herokuapp.com/api/viewpost/1");
                            finish();
                            startActivity( Intent);
                        }
                    });


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


    /*@Override
    public void onClick(View view) {

        Log.e("Button",view.getTag().toString());
        Intent Intent = new Intent(ForumFrontPage.this,SoloPost.class);
        //Log.e("url", objectId[postNo]);
        Intent.putExtra("url","http://192.168.240.17:3000/api/viewIndpost/"+view.getTag().toString());
        finish();
        startActivity( Intent);
    }*/
}

package comps456f.finalyearproject;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class ApiHandler {
    private String data = "";
    private JSONObject inputData = new JSONObject();
    private TextView compilerReturn;

    public TextView getCompilerReturn(){
        return compilerReturn;
    }
    //Get Request
    //Get exam question and answer
    public String getRequest(Context context, String url){

        RequestQueue queue = Volley.newRequestQueue(context);

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
                        //stxt.setText(data);
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
        return data;
    }

    //Post Request
    //Register account
    public void postRequest(Context context, String url){
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            inputData.put("name", "test");
            inputData.put("password","1723");
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, inputData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    data=response.getString("out");
                    Log.e("out", data);

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
    }

    //Compiler
    public void compiler(Context context, String url, String input){
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            inputData.put("code", input);
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, inputData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(!response.optString("out").equals("")){
                    compilerReturn.setText(response.optString("out"));
                }else if(!response.optString("compileErr").equals("")){
                    //compilerReturn = response.optString("compileErr");
                }else if(!response.optString("runtimeErr").equals("")){
                    //compilerReturn = response.optString("compileErr");
                }
                Log.e("OnResponse",response.optString("out"));

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("CheckFAIL", error.toString());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}

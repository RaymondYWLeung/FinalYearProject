package comps456f.finalyearproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText et_user, et_password,et_nickname;
    private Button but_signin,but_register;
    private ProgressDialog progressDialog;
    private boolean booSign = false,booReg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_user = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_nickname = findViewById(R.id.et_nickName);
        but_signin = findViewById(R.id.but_Signin);
        but_signin.setOnClickListener(this);
        RadioGroup rg = findViewById(R.id.radio_gp);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int radioButtonId = group.getCheckedRadioButtonId();
                if(radioButtonId == R.id.rb_Signin){
                    et_nickname.setEnabled(false);
                    et_nickname.setBackgroundColor(getResources().getColor(R.color.grey));
                    booSign = true;
                    booReg = false;
                }else{
                    et_nickname.setEnabled(true);
                    et_nickname.setBackgroundColor(getResources().getColor(R.color.ide_white));
                    booSign = false;
                    booReg = true;
                }
            }
        });

        RadioButton rb_signin = findViewById(R.id.rb_Signin);
        RadioButton rb_register = findViewById(R.id.rb_Register);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        JSONObject inputData = new JSONObject();
        String mUsername = et_user.getText().toString();
        String mPassword = et_password.getText().toString();
        String mNickname = et_nickname.getText().toString();
        String login_url = "http://192.168.240.17:3000/users/login";
        String reg_url =  "http://192.168.240.17:3000/users/register";
        final Context appContext = getApplicationContext();
        if(!booReg && !booSign) {
            et_user.setError("Please select Sign in or Register");
            findViewById(R.id.rb_Signin).requestFocus();

        }else if(mUsername.equals("")) {
            et_user.setError("Username cannot be empty");
            et_user.requestFocus();

        }else if(mPassword.equals("") ) {
            et_user.setError("Password cannot be empty");
            et_user.requestFocus();

        }else if(id == R.id.but_Signin && booSign ){
            RequestQueue queue = Volley.newRequestQueue(appContext);

            try {
                inputData.put("userName", mUsername);
                inputData.put("password",mPassword);
            } catch (JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, login_url, inputData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("LoginUI", response.optString("nickName"));

                    SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                    if(response.optString("status").equals("Login Successful")) {
                        pref.edit()
                                .putString("STATUS", response.optString("status"))
                                .putString("NICKNAME", response.optString("nickName"))
                                .putString("ID", response.optString("objID"))
                                .commit();
                        progressDialog.dismiss();
                        finish();
                    }else{
                        progressDialog.dismiss();
                        et_user.setError(response.optString("reason"));
                        et_user.requestFocus();
                    }
                }

            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.e("CheckFAIL", error.toString());
                    progressDialog.dismiss();
                    finish();
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logging ....");
            progressDialog.show();

        }else if(id == R.id.but_Signin && booReg) {
            if (mNickname.equals("")) {
                et_nickname.setError("Nickname cannot be empty");
                et_nickname.requestFocus();

            } else {
                    RequestQueue queue = Volley.newRequestQueue(appContext);

                    try {
                        inputData.put("userName", mUsername);
                        inputData.put("password", mPassword);
                        inputData.put("nickName", mNickname);
                    } catch (JSONException e) {
                        Log.e("MYAPP", "unexpected JSON exception", e);
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, reg_url, inputData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("LoginUI", response.optString("nickName"));

                            SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                            if (response.optString("status").equals("User account created")) {
                                pref.edit()
                                        .putString("STATUS", response.optString("status"))
                                        .putString("NICKNAME", response.optString("nickName"))
                                        .putString("ID", response.optString("objID"))
                                        .commit();
                                progressDialog.dismiss();
                                et_user.clearFocus();
                                et_password.clearFocus();
                                Toast.makeText(getApplicationContext(),"Please Login again ",Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                et_user.setError(response.optString("status"));
                                et_user.requestFocus();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("CheckFAIL", error.toString());
                            progressDialog.dismiss();
                            finish();
                        }
                    });
                    request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(request);
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Connecting ....");
                    progressDialog.show();
                }
            }
        }

}

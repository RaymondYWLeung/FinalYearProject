package comps456f.finalyearproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Compiler extends AppCompatActivity implements View.OnClickListener{

    private EditText etCode;
    private TextView output;
    private ProgressDialog progressDialog;

    String compile_url = "https://raymondsfypapi.herokuapp.com/api/compile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Compiler");

        etCode = (EditText) findViewById(R.id.compiler_code);
        new CodeIDE(etCode.getText(),getResources());
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                new CodeIDE(editable,getResources(),etCode.getText().length());
            }
        });

        Button compile_but = (Button)findViewById(R.id.compile_but);
        compile_but.setOnClickListener(this);

        output = (TextView) findViewById(R.id.compile_result);
    }

    @Override
    public void onClick(View view) {

        JSONObject inputData = new JSONObject();
        String input = etCode.getText().toString();
        String url = compile_url;
        final Context appContext = getApplicationContext();

        int id = view.getId();

        if(id == R.id.compile_but){

            RequestQueue queue = Volley.newRequestQueue(appContext);

            try {
                inputData.put("code", input);
            } catch (JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, inputData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if(!response.optString("out").equals("")){
                        output.setText(response.optString("out"));
                    }else if(!response.optString("compileErr").equals("")){
                        output.setText(response.optString("compileErr"));
                    }else if(!response.optString("runtimeErr").equals("")){
                        output.setText(response.optString("runtimeErr"));
                    }
                    Log.e("OnResponse",response.optString("out"));
                    progressDialog.dismiss();

                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.e("CheckFAIL", error.toString());
                    progressDialog.dismiss();
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Compiling ....");
            progressDialog.show();

        }

    }
}

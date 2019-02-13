package comps456f.finalyearproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler extends AppCompatActivity {

    private EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Compiler");

        etCode = (EditText) findViewById(R.id.compiler_code);
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*CodeIDE aIDE = new CodeIDE();
                String result = aIDE.changeColor(charSequence.toString());
                Toast.makeText(getApplication(),result,Toast.LENGTH_SHORT).show();*/


                }

            @Override
            public void afterTextChanged(Editable editable) {

                Spannable textSpan = editable;
                final Pattern pattern = Pattern.compile("(public)");
                final Matcher matcher = pattern.matcher(textSpan);
                while (matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();
                    textSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
        });

    }
}

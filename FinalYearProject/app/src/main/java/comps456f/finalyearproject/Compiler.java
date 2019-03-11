package comps456f.finalyearproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

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

                ForegroundColorSpan[] spannable = editable.getSpans(0, etCode.getText().length() - 1, ForegroundColorSpan.class);
                Log.e("234",spannable.length+"");
                if (spannable != null && spannable.length > 0) {
                    for (int i = 0; i < spannable.length; i++) {
                        editable.removeSpan(spannable[i]);
                    }
                }

                CodeIDE ide = new CodeIDE();
                String red_key_word = "\\b(public)\\b|\\b(static)\\b|(\\[\\])|\\b(new)\\|\\b(for)\\b|\\b(if)\\b" +
                        "|\\b(else)\\b|\\b(else if)\\b|\\b(=)\\b" ;
                String blue_key_word = "\\b(String)\\b|\\b(System)\\b|\\b(print)\\b|\\b(println)\\b" ;

                ide.changeColor(editable,red_key_word,getResources().getColor(R.color.ide_red));
                ide.changeColor(editable,blue_key_word,getResources().getColor(R.color.ide_blue));


            }
        });

    }
}

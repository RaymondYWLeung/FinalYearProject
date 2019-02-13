package comps456f.finalyearproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Examination1 extends AppCompatActivity implements View.OnClickListener{

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination1);
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
            case R.id.exam1_q1c:
                if (checked)
                    score += 1;
                    // Pirates are the best
                    break;
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.exam1_submit_button){

        }

    }
}

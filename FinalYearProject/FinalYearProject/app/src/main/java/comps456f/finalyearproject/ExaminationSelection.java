package comps456f.finalyearproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ExaminationSelection extends AppCompatActivity implements View.OnClickListener{

    private Button exam_but[] = new Button[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Examintation");

        for(int i = 0 ; i < exam_but.length ; i++){
            String idName = "exam_" + (i+1);
            Log.d("exam_but", idName);

            exam_but[i] = (Button)findViewById(getResources().getIdentifier(idName, "id", getPackageName()));
            exam_but[i].setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.exam_1){
            Intent act = new Intent(ExaminationSelection.this,Examination1.class);
            startActivity(act);
        }

    }
}


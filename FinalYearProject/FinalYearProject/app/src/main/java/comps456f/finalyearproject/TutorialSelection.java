package comps456f.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class TutorialSelection extends AppCompatActivity implements View.OnClickListener{

    private Button tutor_but[] = new Button[8];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tutorial");

        for(int i = 0 ; i < tutor_but.length ; i++){
            String idName = "tutorial_" + (i+1);
            //System.out.println(idName);
            tutor_but[i] = (Button)findViewById(getResources().getIdentifier(idName, "id", getPackageName()));
            tutor_but[i].setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.tutorial_1){
            Intent act = new Intent(TutorialSelection.this,Tutorial1.class);
            startActivity(act);
        }

    }
}

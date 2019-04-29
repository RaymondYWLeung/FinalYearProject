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

    private Button tutor_but[] = new Button[17];


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
            Intent act = new Intent(TutorialSelection.this,Tutorial01.class);
            startActivity(act);
        }else if(id == R.id.tutorial_2){
            Intent act = new Intent(TutorialSelection.this,Tutorial02.class);
            startActivity(act);
        }else if(id == R.id.tutorial_3){
            Intent act = new Intent(TutorialSelection.this,Tutorial03.class);
            startActivity(act);
        }else if(id == R.id.tutorial_4){
            Intent act = new Intent(TutorialSelection.this,Tutorial04.class);
            startActivity(act);
        }else if(id == R.id.tutorial_5){
            Intent act = new Intent(TutorialSelection.this,Tutorial05.class);
            startActivity(act);
        }else if(id == R.id.tutorial_6){
            Intent act = new Intent(TutorialSelection.this,Tutorial06.class);
            startActivity(act);
        }else if(id == R.id.tutorial_7){
            Intent act = new Intent(TutorialSelection.this,Tutorial07.class);
            startActivity(act);
        }else if(id == R.id.tutorial_8){
            Intent act = new Intent(TutorialSelection.this,Tutorial08.class);
            startActivity(act);
        }else if(id == R.id.tutorial_9){
            Intent act = new Intent(TutorialSelection.this,Tutorial09.class);
            startActivity(act);
        }else if(id == R.id.tutorial_10){
            Intent act = new Intent(TutorialSelection.this,Tutorial10.class);
            startActivity(act);
        }else if(id == R.id.tutorial_11){
            Intent act = new Intent(TutorialSelection.this,Tutorial11.class);
            startActivity(act);
        }else if(id == R.id.tutorial_12){
            Intent act = new Intent(TutorialSelection.this,Tutorial12.class);
            startActivity(act);
        }else if(id == R.id.tutorial_13){
            Intent act = new Intent(TutorialSelection.this,Tutorial13.class);
            startActivity(act);
        }else if(id == R.id.tutorial_14){
            Intent act = new Intent(TutorialSelection.this,Tutorial14.class);
            startActivity(act);
        }else if(id == R.id.tutorial_15){
            Intent act = new Intent(TutorialSelection.this,Tutorial15.class);
            startActivity(act);
        }else if(id == R.id.tutorial_16) {
            Intent act = new Intent(TutorialSelection.this, Tutorial16.class);
            startActivity(act);
        }else if(id == R.id.tutorial_17) {
            Intent act = new Intent(TutorialSelection.this, Tutorial17.class);
            startActivity(act);
        }
    }
}

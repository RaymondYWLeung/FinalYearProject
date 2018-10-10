package comps456f.finalyearproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Tutorial1 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tutorial1);
        Button button01 = (Button)findViewById(R.id.but2c_compile);
        button01.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id == R.id.but2c_compile){
            Toast.makeText(this, "Compile Successful", Toast.LENGTH_SHORT).show();
        }

    }
}

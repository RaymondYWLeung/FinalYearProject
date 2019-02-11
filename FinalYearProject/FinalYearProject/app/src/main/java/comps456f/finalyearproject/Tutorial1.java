package comps456f.finalyearproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.internal.util.*;

import java.io.File;
import java.io.FileOutputStream;

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
            //Log.d("123","123!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //Toast.makeText(this, "Compiling", Toast.LENGTH_SHORT).show();
            SaveJava sj = new SaveJava();
            String path = sj.saveAsJava(getString(R.string.tutorial1_hello_world_example));
            Log.d("Save_As_Java",path);
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            //Launcher launcher = new Launcher();

        }

    }
}

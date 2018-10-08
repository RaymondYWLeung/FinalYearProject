package comps456f.finalyearproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Tutorial1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tutorial1);
    }
}

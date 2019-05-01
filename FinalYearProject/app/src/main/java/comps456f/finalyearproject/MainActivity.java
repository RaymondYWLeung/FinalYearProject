package comps456f.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerView.findViewById(R.id.nav_header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act = new Intent(MainActivity.this,Login.class);
                startActivity(act);
            }
        });

        String nickName = getSharedPreferences("user", MODE_PRIVATE).getString("USER", "Guest");

        //if(nickName.equals("Login")){

        TextView nav_nickName  = header.findViewById(R.id.nickname);
        nav_nickName.setText(nickName);
        //}

    }
    @Override
    protected void onRestart() {
        super.onRestart();

        String nickName = getSharedPreferences("user", MODE_PRIVATE).getString("NICKNAME", "Guest");
        Log.e("onRestart()",nickName);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerView.findViewById(R.id.nav_header);
        TextView nav_nickName  = header.findViewById(R.id.nickname);
        nav_nickName.setText(nickName);
        //}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        String nickName = getSharedPreferences("user", MODE_PRIVATE).getString("NICKNAME", "Login1111");
        Log.e("Drawer",nickName);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);
            LinearLayout header = (LinearLayout) headerView.findViewById(R.id.nav_header);
            TextView nav_nickName  = header.findViewById(R.id.nickname);
            nav_nickName.setText(nickName);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tutorial) {

            Intent act = new Intent(MainActivity.this,TutorialSelection.class);
            startActivity(act);

        } else if (id == R.id.nav_exam) {
            Intent act = new Intent(MainActivity.this,ExaminationSelection.class);
            startActivity(act);

        } else if (id == R.id.nav_forum) {
            Intent act = new Intent(MainActivity.this,ForumFrontPage.class);
            startActivity(act);
        } else if (id == R.id.nav_compiler) {
            Intent act = new Intent(MainActivity.this,Compiler.class);
            startActivity(act);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

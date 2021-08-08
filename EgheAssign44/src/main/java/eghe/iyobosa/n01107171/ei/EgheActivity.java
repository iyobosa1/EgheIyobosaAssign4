package eghe.iyobosa.n01107171.ei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class EgheActivity extends AppCompatActivity {

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eghe);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.egheNav_view);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browser);
        return super.onOptionsItemSelected(item);
    }

    public void choiceAction(MenuItem item) {
        Intent i = new Intent(getApplicationContext(),EgheActivity.class);
        startActivity(i);

    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.nhungHome:
//                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NhHome()).commit();
//                break;
//            case R.id.nhungNhung:
//                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NhuDown()).commit();
//
//                break;
//            case R.id.nhungNguyen:
//                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NgSrv()).commit();
//                break;
//            case R.id.nhungSetting:
//                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NhuSet()).commit();
//                break;
//
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
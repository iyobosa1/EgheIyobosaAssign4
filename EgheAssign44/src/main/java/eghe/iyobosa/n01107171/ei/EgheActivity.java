package eghe.iyobosa.n01107171.ei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EgheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
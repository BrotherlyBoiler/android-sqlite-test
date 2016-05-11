package bamtastic.sqlitetext1;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getBaseContext().openOrCreateDatabase("sqlite-test-1.db", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS contacts(name TEXT, phone INTEGER, email TEXT)");
            sqLiteDatabase.execSQL("INSERT INTO contacts VALUES('Tim', 1234, 'tim@email.com')");
            sqLiteDatabase.execSQL("INSERT INTO contacts VALUES('Fred', 1234, 'fred@bmail.com')");

            Cursor cursor = null;
            try {
                cursor = sqLiteDatabase.rawQuery("SELECT * from contacts", null);
                if (cursor.moveToFirst()) {
                    // Cycle through all records
                    do {
                        String name = cursor.getString(0);
                        int phone = cursor.getInt(1);
                        String email = cursor.getString(2);

                        String testOutput = "Name = " + name + "\nphone = " + phone + "\nemail = " + email;
                        Toast.makeText(MainActivity.this, testOutput, Toast.LENGTH_SHORT).show();
                    } while (cursor.moveToNext());
                } else {
                    Toast.makeText(MainActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
                }
            } finally {
                if (cursor != null) cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) sqLiteDatabase.close();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                          .setAction("Show", new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  Snackbar.make(v, "Callback", Snackbar.LENGTH_LONG).show();
                              }
                          }).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

package a123.com.example.vgani.trainingpref;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String MY_TEXT = "mytext";
    EditText mEditText;
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSave:
                saveText();
                break;
            case R.id.buttonLoad:
                loadText();
                break;
            case R.id.buttonSaveInternal:
                saveInternalFile("Happy.txt", "This any information");
                break;
            case R.id.buttonReadInternal:
                Toast.makeText(this, readInternalFile("Happy.txt"), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonSaveonSd:
                if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                }else {
                    Toast.makeText(this,"Sorry no permission",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 1, 0, "Pref");
        item.setIntent(new Intent(this, PreferenceActivitySec.class));
        return super.onCreateOptionsMenu(menu);

    }

    private void saveText() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MY_TEXT, mEditText.getText().toString());
        editor.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    private void loadText() {
        SharedPreferences preferences1 = getPreferences(MODE_PRIVATE);
        String text = preferences1.getString(MY_TEXT, "");
        mEditText.setText(text);
    }

    private void saveInternalFile(String filename, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))
            );
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readInternalFile(String fileName) {
        try {
            StringBuilder builder = new StringBuilder();
            //Поток для чтения файла
            InputStream inputStream = openFileInput(fileName);
            InputStreamReader reader = new InputStreamReader(inputStream);

            //Чтение файла по строкам
            BufferedReader reader1 = new BufferedReader(reader);
            String line;

            while ((line = reader1.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, permission)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void saveExternalFile(File folder, String filename, String data) {
        File file = new File(folder, filename);

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file), "UTF8")
            );

            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

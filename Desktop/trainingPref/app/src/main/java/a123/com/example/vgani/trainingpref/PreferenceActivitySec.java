package a123.com.example.vgani.trainingpref;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class PreferenceActivitySec extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}

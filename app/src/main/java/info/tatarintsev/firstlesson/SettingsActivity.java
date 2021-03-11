package info.tatarintsev.firstlesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Settings settings = getIntent().getExtras().getParcelable("CalculatorSettings");

        SwitchCompat switchOfNightTheme = (SwitchCompat) findViewById(R.id.settings_night_theme_switch);
        switchOfNightTheme.setChecked(settings.isNightTheme());

        Button btnReturn = (Button)findViewById(R.id.settings_button_back);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                intentResult.putExtra("CalculatorSettings", new Settings(switchOfNightTheme.isChecked()));
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });
    }
}
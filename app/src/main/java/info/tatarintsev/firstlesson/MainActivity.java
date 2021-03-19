package info.tatarintsev.firstlesson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    public static NoticeData[] m_notices;

    {
        // немного хардкода
        m_notices = new NoticeData[3];
        String[] titles = getResources().getStringArray(R.array.title);
        String[] descriptions = getResources().getStringArray(R.array.description);
        String[] dates = getResources().getStringArray(R.array.where);
        for (int i = 0; i < 3; i++) {
            try {
                m_notices[i] = new NoticeData(titles[i], descriptions[i], dates[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
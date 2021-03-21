package info.tatarintsev.firstlesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    public static NoticeData[] m_notices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
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
        NoticeListFragment fragment = NoticeListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_list, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }
}
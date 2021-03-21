package info.tatarintsev.firstlesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

        initToolbar();

        NoticeListFragment fragment = NoticeListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_list, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void addBasicFragment(Fragment fragmentToAdd) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        List<Fragment> visibleList = new ArrayList<>();
        for (Fragment fragment: fragmentList) {
            if(fragment != null && fragment.isVisible()) {
                visibleList.add(fragment);
            }
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack(CURRENT_NOTE);
        for (Fragment fragment: visibleList) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.replace(R.id.notes_list, fragmentToAdd);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                addBasicFragment(new SettingsFragment());
                return true;
            case R.id.action_main:
                addBasicFragment(new AddNoticeFragment());
                return true;
            case R.id.action_favorite:
                addBasicFragment(new NoticeListFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }
            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

}
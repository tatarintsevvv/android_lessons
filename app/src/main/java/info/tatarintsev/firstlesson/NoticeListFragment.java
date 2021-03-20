package info.tatarintsev.firstlesson;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NoticeListFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote";
    private NoticeData currentNotice;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notice_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linear = (LinearLayout)view;
        String[] titles = getResources().getStringArray(R.array.title);
        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(titles[i]);
            textView.setTextSize(40);
            linear.addView(textView);
            final int index = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        currentNotice = new NoticeData(getResources().getStringArray(R.array.title)[index],
                                getResources().getStringArray(R.array.description)[index],
                                getResources().getStringArray(R.array.where)[index]);
                        showDetailFragment(currentNotice);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNotice);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if(savedInstanceState != null) {
            currentNotice = savedInstanceState.getParcelable(CURRENT_NOTE);
        }

        if(isLandscape) {
            showLandscapeDetailFragment(currentNotice);
        }
    }

    private void showDetailFragment(NoticeData notice) {
        if(isLandscape) {
            showLandscapeDetailFragment(notice);
        } else {
            showPortraitDetailFragment(notice);
        }
    }

    private void showPortraitDetailFragment(NoticeData notice) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoticeDetailActivity.class);
        intent.putExtra(NoticeDetailFragment.FRAGMENT_TOKEN, notice);
        startActivity(intent);
    }

    private void showLandscapeDetailFragment(NoticeData notice) {
        NoticeDetailFragment detail = NoticeDetailFragment.newInstance(notice);
        FragmentManager fragmentManager = requireActivity()
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

}
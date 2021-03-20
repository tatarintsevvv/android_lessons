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

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NoticeListFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote";
    private int currentPosition = 0;
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
                    currentPosition = index;
                    showDetailFragment(index);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if(savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE);
        }

        if(isLandscape) {
            showLandscapeDetailFragment(currentPosition);
        }
    }

    private void showDetailFragment(int index) {
        if(isLandscape) {
            showLandscapeDetailFragment(index);
        } else {
            showPortraitDetailFragment(index);
        }
    }

    private void showPortraitDetailFragment(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoticeDetailActivity.class);
        intent.putExtra(NoticeDetailFragment.FRAGMENT_TOKEN, index);
        startActivity(intent);
    }

    private void showLandscapeDetailFragment(int index) {
        NoticeDetailFragment detail = NoticeDetailFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity()
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

}
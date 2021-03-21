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
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NoticeListFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote";
    private NoticeData currentNotice;
    private boolean isLandscape;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NoticeListFragment.
     */
    public static NoticeListFragment newInstance() {
        return new NoticeListFragment();
    }


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
            textView.setOnClickListener(v -> {
                try {
                    currentNotice = new NoticeData(getResources().getStringArray(R.array.title)[index],
                            getResources().getStringArray(R.array.description)[index],
                            getResources().getStringArray(R.array.where)[index]);
                    showDetailFragment(currentNotice);
                } catch (ParseException e) {
                    e.printStackTrace();
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

        if(isLandscape && currentNotice != null) {
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
        /*
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoticeDetailActivity.class);
        intent.putExtra(NoticeDetailFragment.FRAGMENT_TOKEN, notice);
        startActivity(intent);
         */
        NoticeDetailFragment detail = NoticeDetailFragment.newInstance(notice);
        FragmentManager fragmentManager = requireActivity()
                .getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        List<Fragment> visibleList = new ArrayList<>();
        for (Fragment fragment: fragmentList) {
            if(fragment != null && fragment.isVisible()) {
                visibleList.add(fragment);
            }
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(CURRENT_NOTE);
        for (Fragment fragment: visibleList) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.replace(R.id.notes_list, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
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
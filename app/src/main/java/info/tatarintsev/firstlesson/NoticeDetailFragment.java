package info.tatarintsev.firstlesson;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoticeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeDetailFragment extends Fragment {

    private static final String FRAGMENT_TOKEN = "index";
    private int mIndex;

    public NoticeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter 1.
     * @return A new instance of fragment NoticeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticeDetailFragment newInstance(String param) {
        NoticeDetailFragment fragment = new NoticeDetailFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TOKEN, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(FRAGMENT_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_detail, container, false);
        NoticeData notice = MainActivity.m_notices[mIndex];
        TextView textView = view.findViewById(R.id.title_detail);
        textView.setText(notice.getTitle());

        textView = view.findViewById(R.id.description_detail);
        textView.setText(notice.getDescription());
        textView.setTextSize(24);

        Date dateOfNotice = notice.getDateCreate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfNotice);
        DatePicker datePicker = view.findViewById(R.id.date_detail);
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    }
                });
        return view;
    }
}
package info.tatarintsev.firstlesson.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

import info.tatarintsev.firstlesson.data.NoticeData;
import info.tatarintsev.firstlesson.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoticeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeDetailFragment extends Fragment {

    public static final String FRAGMENT_TOKEN = "notice";
    private NoticeData mNotice;

    public NoticeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param notice заметка
     * @return A new instance of fragment NoticeDetailFragment.
     */
    // для редактирвоания
    public static NoticeDetailFragment newInstance(NoticeData notice) {
        NoticeDetailFragment fragment = new NoticeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(FRAGMENT_TOKEN, notice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNotice = getArguments().getParcelable(FRAGMENT_TOKEN);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_detail, container, false);
        NoticeData notice = mNotice;
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
                null);
        return view;
    }


}
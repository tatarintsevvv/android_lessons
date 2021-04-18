package info.tatarintsev.firstlesson.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import info.tatarintsev.firstlesson.MainActivity;
import info.tatarintsev.firstlesson.data.NoticeData;
import info.tatarintsev.firstlesson.R;
import info.tatarintsev.firstlesson.observe.Publisher;

public class EditNoticeFragment extends Fragment {

    public static final String FRAGMENT_TOKEN = "edit_notice";
    private NoticeData mNotice;
    private Publisher publisher;

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;
    private MaterialButton saveButton;

    // при добавлении заметки
    public static EditNoticeFragment newInstance() {
        return new EditNoticeFragment();
    }

    // при изменении заметки
    public static EditNoticeFragment newInstance(NoticeData notice) {
        EditNoticeFragment fragment = new EditNoticeFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }


    // Здесь соберём данные из views
    @Override
    public void onStop() {
        super.onStop();
        mNotice = collectNoticeData();
    }

    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(mNotice);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_notice_fragment, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (mNotice != null) {
            populateView();
        }
        return view;
    }


    private NoticeData collectNoticeData(){
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();
/*
        if(noticeData != null) {
            NoticeData answer;
            answer = new NoticeData(title, description, date);
            answer.setId(noticeData.getId());
            return answer;

        } else {

        }

 */
        return new NoticeData(title, description,  date);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        Fragment currentActivity = this;
        title = view.findViewById(R.id.title_edit);
        description = view.findViewById(R.id.description_edit);
        datePicker = view.findViewById(R.id.date_edit);
        saveButton = view.findViewById(R.id.edit_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeData noticeData = collectNoticeData();
                publisher.notifySingle(noticeData);
            }
        });
    }

    private void populateView(){
        title.setText(mNotice.getTitle());
        description.setText(mNotice.getDescription());
        initDatePicker(mNotice.getDateCreate());
    }

    // Установка даты в DatePicker
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

}
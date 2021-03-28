package info.tatarintsev.firstlesson.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.tatarintsev.firstlesson.EditNoticeFragment;
import info.tatarintsev.firstlesson.MainActivity;
import info.tatarintsev.firstlesson.Navigation;
import info.tatarintsev.firstlesson.NoticeData;
import info.tatarintsev.firstlesson.NoticeDetailFragment;
import info.tatarintsev.firstlesson.NoticeSource;
import info.tatarintsev.firstlesson.NoticeSourceImpl;
import info.tatarintsev.firstlesson.R;
import info.tatarintsev.firstlesson.observe.Observer;
import info.tatarintsev.firstlesson.observe.Publisher;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NoticeListFragment extends Fragment {

    private static final int DEFAULT_DURATION = 1000;
    private NoticeSource data;
    private NoticeListAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    // признак, что при повторном открытии фрагмента
    // (возврате из фрагмента, добавляющего запись)
    // надо прыгнуть на последнюю запись
    private boolean moveToLastPosition;

    private static final String CURRENT_NOTE = "CurrentNote";

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Получим источник данных для списка
        // Поскольку onCreateView запускается каждый раз
        // при возврате в фрагмент, данные надо создавать один раз
        data = new NoticeSourceImpl(getResources()).init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.notices_recycler_view);
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notices_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                navigation.addFragment(EditNoticeFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoticeData(NoticeData noticeData) {
                        data.addNoticeData(noticeData);
                        adapter.notifyItemInserted(data.size() - 1);
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на конец списка
                        moveToLastPosition = true;
                    }
                });
                return true;
            case R.id.action_clear:
                data.clearNoticeData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.notices_recycler_view);
        // Получим источник данных для списка
        data = new NoticeSourceImpl(getResources()).init();
        initRecyclerView();
    }

    private void initRecyclerView(){

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        adapter = new NoticeListAdapter(data, this);
        recyclerView.setAdapter(adapter);

        if (moveToLastPosition){
            recyclerView.smoothScrollToPosition(data.size() - 1);
            moveToLastPosition = false;
        }

        // Установим слушателя
        adapter.SetOnItemClickListener(new NoticeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
            }
        });

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(DEFAULT_DURATION);
        animator.setRemoveDuration(DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.notice_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch(item.getItemId()) {
            case R.id.action_update:
                /*
                data.updateNoticeData(position,
                        new NoticeData(data.getNoticeData(position).getTitle(),
                                data.getNoticeData(position).getDescription(),
                                data.getNoticeData(position).getDateCreate()));
                adapter.notifyItemChanged(position);

                 */
                navigation.addFragment(EditNoticeFragment.newInstance(data.getNoticeData(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoticeData(NoticeData noticeData) {
                        data.updateNoticeData(position, noticeData);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                data.deleteNoticeData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


}
package info.tatarintsev.firstlesson.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import info.tatarintsev.firstlesson.NoticeData;
import info.tatarintsev.firstlesson.NoticeSource;
import info.tatarintsev.firstlesson.R;

public class NoticeListAdapter
    extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {

    private final static String TAG = "NoticeListAdapter";
    private NoticeSource dataSource;
    private AdapterView.OnItemClickListener itemClickListener;

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public NoticeListAdapter(NoticeSource dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public NoticeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notice_item, viewGroup, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull NoticeListAdapter.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        viewHolder.setData(dataSource.getNoticeData(i));
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = (AdapterView.OnItemClickListener) itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;
        private TextView title;
        private TextView description;
        private TextView dateCreate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            title = itemView.findViewById(R.id.title_detail);
            description = itemView.findViewById(R.id.description_detail);


            linearLayout.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

        }

        public void setData(NoticeData noticeData){
            title.setText(noticeData.getTitle());
            description.setText(noticeData.getDescription());
            Date date = noticeData.getDateCreate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String strDateCreate = simpleDateFormat.format(date);
            dateCreate.setText(strDateCreate);
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }
}

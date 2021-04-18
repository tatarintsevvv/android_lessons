package info.tatarintsev.firstlesson.data;

import android.content.res.Resources;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import info.tatarintsev.firstlesson.R;

public class NoticeSourceImpl  implements NoticeSource {
    private List<NoticeData> dataSource;
    private Resources resources;    // ресурсы приложения

    public NoticeSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public NoticeSourceImpl init(NoticeSourceResponse noticeSourceResponse){
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.title);
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.description);
        // изображения
        String[] dates = resources.getStringArray(R.array.where);

        // заполнение источника данных
        for (int i = 0; i < descriptions.length; i++) {
            try {
                dataSource.add(new NoticeData(titles[i], descriptions[i], dates[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(noticeSourceResponse != null) {
            noticeSourceResponse.initialized(this);
        }
        return this;
    }

    public NoticeData getNoticeData(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    @Override
    public void deleteNoticeData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateNoticeData(int position, NoticeData noticeData) {
        dataSource.set(position, noticeData);
    }

    @Override
    public void addNoticeData(NoticeData noticeData) {
        dataSource.add(noticeData);
    }

    @Override
    public void clearNoticeData() {
        dataSource.clear();
    }
}


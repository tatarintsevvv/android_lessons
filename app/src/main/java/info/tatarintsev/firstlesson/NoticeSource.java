package info.tatarintsev.firstlesson;

public interface NoticeSource {
    NoticeData getNoticeData(int position);
    int size();
    void deleteNoticeData(int position);
    void updateNoticeData(int position, NoticeData noticeData);
    void addNoticeData(NoticeData noticeData);
    void clearNoticeData();
}

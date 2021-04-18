package info.tatarintsev.firstlesson.data;

public interface NoticeSource {
    NoticeSource init(NoticeSourceResponse noticeSourceResponse);
    NoticeData getNoticeData(int position);
    int size();
    void deleteNoticeData(int position);
    void updateNoticeData(int position, NoticeData noticeData);
    void addNoticeData(NoticeData noticeData);
    void clearNoticeData();
}

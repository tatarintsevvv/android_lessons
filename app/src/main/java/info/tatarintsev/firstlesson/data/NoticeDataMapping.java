package info.tatarintsev.firstlesson.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

import info.tatarintsev.firstlesson.NoticeData;

public class NoticeDataMapping {
    public static class Fields {
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
    }

    public static NoticeData toNoticeData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        NoticeData answer = new NoticeData((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NoticeData noticeData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, noticeData.getTitle());
        answer.put(Fields.DESCRIPTION, noticeData.getDescription());
        answer.put(Fields.DATE, noticeData.getDateCreate());
        return answer;
    }

}

package info.tatarintsev.firstlesson;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeData implements Parcelable {
    String m_title;
    String m_description;
    Date m_dateCreate;

    public NoticeData(String title, String description, Date dateCreate) {
        this.m_title = title;
        this.m_description = description;
        this.m_dateCreate = dateCreate;
    }

    public NoticeData(String title, String description, String strDateCreate) throws ParseException {
        this.m_title = title;
        this.m_description = description;
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        m_dateCreate = dateFormat.parse(strDateCreate);
    }

    public NoticeData() {
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String m_title) {
        this.m_title = m_title;
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String m_description) {
        this.m_description = m_description;
    }

    public Date getDateCreate() {
        return m_dateCreate;
    }

    public void setDateCreate(Date m_dateCreate) {
        this.m_dateCreate = m_dateCreate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_title);
        dest.writeString(m_description);
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        dest.writeString(dateFormat.format(m_dateCreate));
    }

    public static final Parcelable.Creator<NoticeData> CREATOR = new Parcelable.Creator<NoticeData>() {
        // распаковываем объект из Parcel
        public NoticeData createFromParcel(Parcel in) {
            NoticeData noticeData = null;
            try {
                noticeData = new NoticeData(in);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return noticeData;
        }

        public NoticeData[] newArray(int size) {
            return new NoticeData[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private NoticeData(Parcel parcel) throws ParseException {
        m_title = parcel.readString();
        m_description = parcel.readString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        m_dateCreate = dateFormat.parse(parcel.readString());
    }
}

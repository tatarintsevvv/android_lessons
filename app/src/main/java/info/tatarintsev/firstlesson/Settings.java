package info.tatarintsev.firstlesson;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable {
    private boolean isNightTheme;

    protected Settings(Parcel in) {
        isNightTheme = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isNightTheme ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    public boolean isNightTheme() {
        return isNightTheme;
    }

    public void setNightTheme(boolean nightTheme) {
        isNightTheme = nightTheme;
    }

    public Settings() {
    }

    public Settings(boolean isNightTheme) {
        this.isNightTheme = isNightTheme;
    }
}

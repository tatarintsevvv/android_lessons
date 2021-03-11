package info.tatarintsev.firstlesson;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CalculatorData implements Parcelable {
    private String mFirstValue = "";
    // (положительное/отрицательное)
    private int mFirstSign = 1;
    private String mSecondValue = "";
    // (положительное/отрицательное)
    private int mSecondSign = 1;
    private int mAction = 0;
    // десятичный разделитель
    private String mMathSeparator;
    // максимальное число знаков без учета знака числа (положительное/отрицательное)
    private int mMaxSymbols = 9;

    public CalculatorData(int mMaxSymbols) {
        this.mMaxSymbols = mMaxSymbols;
    }

    public static final Creator<CalculatorData> CREATOR = new Creator<CalculatorData>() {
        @Override
        public CalculatorData createFromParcel(Parcel parcel) {
            return new CalculatorData(parcel);
        }

        @Override
        public CalculatorData[] newArray(int i) {
            return new CalculatorData[i];
        }
    };

    public CalculatorData(Parcel in) {
        mFirstValue = in.readString();
        mFirstSign = in.readInt();
        mSecondValue = in.readString();
        mSecondSign = in.readInt();
        mAction = in.readInt();
        mMathSeparator = in.readString();
        mMaxSymbols = in.readInt();
    }


    public CalculatorData() {
    }

    public void reInit() {
        mFirstValue = "";
        mFirstSign = 1;
        mSecondValue = "";
        mSecondSign = 1;
        mAction = 0;
    }

    public void addSymbol(String str) {
        if(mAction == 0) {
            if(mFirstValue.length() < mMaxSymbols) {
                mFirstValue += str;
            }
        } else {
            if(mSecondValue.length() < mMaxSymbols) {
                mSecondValue += str;
            }
        }
    }

    public void changeSign() {
        if(mAction == 0) {
            if(mFirstValue.length() > 0) {
                mFirstSign *= -1;
            } else {
                mFirstSign = 1;
            }
        } else {
            if(mSecondValue.length() > 0) {
                mSecondSign *= -1;
            } else {
                mSecondSign = 1;
            }
        }
    }

    private String containsMathSeparator(String str) {
        if (str.length() > 0) {
            if(str.length() < mMaxSymbols && !str.contains(mMathSeparator)) {
                str += mMathSeparator;
            }
        }
        return str;
    }

    public void addMathSeparator() {
        if(mAction == 0) {
            if (mFirstValue.length() > 0) {
                if(mFirstValue.length() < mMaxSymbols && !mFirstValue.contains(mMathSeparator)) {
                    mFirstValue += mMathSeparator;
                }
            } else {
                mFirstValue = "0" + mMathSeparator;
            }
        } else {
            if (mSecondValue.length() > 0) {
                if(mSecondValue.length() < mMaxSymbols && !mSecondValue.contains(mMathSeparator)) {
                    mSecondValue += mMathSeparator;
                }
            } else {
                mSecondValue = "0" + mMathSeparator;
            }
        }
    }

    public void backspase() {
        if(mAction == 0) {
            if(mFirstValue.length() > 0) {
                mFirstValue = mFirstValue.substring(0, mFirstValue.length() - 1);
            }
        } else {
            if(mSecondValue.length() > 0) {
                mSecondValue = mSecondValue.substring(0, mSecondValue.length() - 1);
            }
        }
    }

    public String getFirstValue() {
        return mFirstValue;
    }

    public void setFirstValue(String firstValue) {
        this.mFirstValue = firstValue;
    }

    public int getFirstSign() {
        return mFirstSign;
    }

    public void setFirstSign(int firstSign) {
        this.mFirstSign = firstSign;
    }

    public String getSecondValue() {
        return mSecondValue;
    }

    public void setSecondValue(String secondValue) {
        this.mSecondValue = secondValue;
    }

    public int getSecondSign() {
        return mSecondSign;
    }

    public void setSecondSign(int secondSign) {
        this.mSecondSign = secondSign;
    }

    public int getAction() {
        return mAction;
    }

    public void setAction(int action) {
        this.mAction = action;
    }

    public String getMathSeparator() {
        return mMathSeparator;
    }

    public void setMathSeparator(String mathSeparator) {
        this.mMathSeparator = mathSeparator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFirstValue);
        parcel.writeInt(mFirstSign);
        parcel.writeString(mSecondValue);
        parcel.writeInt(mSecondSign);
        parcel.writeInt(mAction);
        parcel.writeString(mMathSeparator);
        parcel.writeInt(mMaxSymbols);
    }
}

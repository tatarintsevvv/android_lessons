package info.tatarintsev.firstlesson;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

public class SquaredButton extends MaterialButton {


    public SquaredButton(@NonNull Context context) {
        super(context);
    }

    public SquaredButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        final int height = getMeasuredHeight(); // высота
        final int width = getMeasuredWidth(); // ширина

        // теперь задаем новый размер
        setMeasuredDimension(Math.max(width, height), Math.max(width, height));
        setCornerRadius(Math.max(width, height)/2);
    }


}

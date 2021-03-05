package info.tatarintsev.firstlesson;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

public class SquaredImageButton extends AppCompatImageButton {
    public SquaredImageButton(@NonNull Context context) {
        super(context);
    }

    public SquaredImageButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = getMeasuredHeight(); // высота
        final int width = getMeasuredWidth(); // ширина

        // теперь задаем новый размер
        setMeasuredDimension(Math.max(width, height), Math.max(width, height));

    }
}

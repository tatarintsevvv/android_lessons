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
// ширину оставляем такую же как у стандартной кнопки
// высоту выбираем как максимум между стандартной высотой и шириной

        setMeasuredDimension(width, Math.max(width, height));

    }
}

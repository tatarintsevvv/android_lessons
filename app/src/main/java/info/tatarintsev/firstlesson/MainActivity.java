package info.tatarintsev.firstlesson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private final static String keyCalculatorData = "CalculatorData";
    // максимальное число знаков без учета знака числа (положительное/отрицательное)
    private final int mMaxSymbols = 9;

    private CalculatorData mCalcData = new CalculatorData(mMaxSymbols);

    private Button[] mDigitButtons = new Button[10];
    Map<Integer, Object> mActionButtons = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // десятичный разделитель
        DecimalFormat format=(DecimalFormat) NumberFormat.getInstance();
        DecimalFormatSymbols symbols=format.getDecimalFormatSymbols();
        mCalcData.setMathSeparator("" + symbols.getDecimalSeparator());
        mCalcData.setMathSeparator(".");


        Button b = (Button)findViewById(R.id.buttonMathSeparator);
        b.setText(mCalcData.getMathSeparator());

        initDigiButtons();
        initActionButtons();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(keyCalculatorData, mCalcData);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCalcData = (CalculatorData) savedInstanceState.getParcelable(keyCalculatorData);
        showResult();
    }

    private void initActionButtons() {
        mActionButtons.put(R.id.buttonCE, findViewById(R.id.buttonCE));
        mActionButtons.put(R.id.buttonC, findViewById(R.id.buttonC));
        mActionButtons.put(R.id.buttonBackspace, findViewById(R.id.buttonBackspace));
        mActionButtons.put(R.id.buttonDivide, findViewById(R.id.buttonDivide));
        mActionButtons.put(R.id.buttonMultiply, findViewById(R.id.buttonMultiply));
        mActionButtons.put(R.id.buttonMinus, findViewById(R.id.buttonMinus));
        mActionButtons.put(R.id.buttonPlus, findViewById(R.id.buttonPlus));
        mActionButtons.put(R.id.buttonEqual, findViewById(R.id.buttonEqual));
        mActionButtons.put(R.id.buttonMathSeparator, findViewById(R.id.buttonMathSeparator));
        mActionButtons.put(R.id.buttonChangeSign, findViewById(R.id.buttonChangeSign));
        for (Object key : mActionButtons.keySet()) {
            View v = (View) mActionButtons.get(key);
            v.setOnClickListener(onActionListener);
        }
    }

    private void initDigiButtons() {
        // указываем кнопки чисел и привязываем обработчик на клик
        mDigitButtons[0] = findViewById(R.id.button0);
        mDigitButtons[1] = findViewById(R.id.button1);
        mDigitButtons[2] = findViewById(R.id.button2);
        mDigitButtons[3] = findViewById(R.id.button3);
        mDigitButtons[4] = findViewById(R.id.button4);
        mDigitButtons[5] = findViewById(R.id.button5);
        mDigitButtons[6] = findViewById(R.id.button6);
        mDigitButtons[7] = findViewById(R.id.button7);
        mDigitButtons[8] = findViewById(R.id.button8);
        mDigitButtons[9] = findViewById(R.id.button9);
        for (int i = 0; i < mDigitButtons.length; i++) {
            mDigitButtons[i].setOnClickListener(onDigitListener);
        }
    }

    private View.OnClickListener onActionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            switch (viewId) {
                case R.id.buttonCE:
                    break;

                case R.id.buttonC:
                    mCalcData.reInit();
                    break;

                case R.id.buttonBackspace:
                    mCalcData.backspase();
                    break;

                case R.id.buttonMathSeparator:
                    mCalcData.addMathSeparator();
                    break;

                case R.id.buttonEqual:
                    if(mCalcData.getAction() != 0) {
                        if(mCalcData.getSecondValue().length() == 0) {
                            mCalcData.setSecondValue("0");
                        }
                        proccessAction();
                    }
                    break;

                case R.id.buttonPlus:
                case R.id.buttonMinus:
                case R.id.buttonMultiply:
                case  R.id.buttonDivide:
                    if(mCalcData.getSecondValue().length()== 0) {
                        mCalcData.setAction(viewId);
                    } else {
                        proccessAction();
                    }
                    break;

                case R.id.buttonChangeSign:
                    mCalcData.changeSign();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + viewId);
            }
            showResult();
        }
    };

     View.OnClickListener onDigitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            mCalcData.addSymbol(b.getText().toString());
            showResult();
        }
    };


    private void proccessAction() {
        if(mCalcData.getFirstValue().length() > 0 && mCalcData.getAction() != 0 && mCalcData.getSecondValue().length() > 0) {
            float first = Float.parseFloat(mCalcData.getFirstValue());
            first *= mCalcData.getFirstSign();
            float second = Float.parseFloat(mCalcData.getSecondValue());
            second *= mCalcData.getSecondSign();
            float result;
            switch(mCalcData.getAction()) {
                case R.id.buttonPlus:
                    result = first + second;
                    break;

                case R.id.buttonMinus:
                    result = first - second;
                    break;

                case R.id.buttonMultiply:
                    result = first * second;
                    break;

                case R.id. buttonDivide:
                    result = first / second;
                    if(Float.isInfinite(result)) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Деление на ноль запрещено", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    break;


                default:
                    throw new IllegalStateException("Unexpected value: " + mCalcData.getAction());
            }

            if(result >= 0.) {
                mCalcData.setFirstSign(1);
            } else {
                mCalcData.setFirstSign(-1);
                result *= -1;
            }
            mCalcData.setFirstValue(Float.toString(result));
            mCalcData.setSecondValue("");
            mCalcData.setSecondSign(1);
            mCalcData.setAction(0);
            showResult();
        }
    }

    private void showResult() {
        AppCompatTextView t = (AppCompatTextView)findViewById(R.id.text_view_result);
        String sign = "";
        if(mCalcData.getAction() > 0 && mCalcData.getSecondValue().length() > 0) {
            if(mCalcData.getSecondSign() == -1) {
                sign = "-";
            }
            t.setText(sign + mCalcData.getSecondValue());
        } else if(mCalcData.getAction() == 0 && mCalcData.getFirstValue().length() > 0) {
            if(mCalcData.getFirstSign() == -1) {
                sign = "-";
            }
            t.setText(sign + mCalcData.getFirstValue());
        } else {
            sign = "0";
            t.setText(sign);
        }
    }
}


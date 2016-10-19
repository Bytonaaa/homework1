package ru.ifmo.android_2016.calc;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by Alex on 19.10.16.
 */

public final class CalculatorActivity extends Activity implements View.OnClickListener {


    //Init textview
    public TextView resultText;
    public TextView currentBuffer;

    public BigDecimal currentResult = BigDecimal.ZERO;
    public BigDecimal value = BigDecimal.ZERO;
    public String buffer = "";
    public char operation = '0';

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        resultText = (TextView) findViewById(R.id.result);
        currentBuffer = (TextView) findViewById(R.id.resultOperator);

        if (savedInstanceState != null) {
            currentResult = BigDecimal.valueOf(Double.parseDouble(savedInstanceState.getString("result")));
            value = BigDecimal.valueOf(Double.parseDouble(savedInstanceState.getString("value", "0")));
            operation = savedInstanceState.getChar("operation", '0');
        }


        resultText.setText(currentResult.toString());
        resultText.setMovementMethod(new ScrollingMovementMethod());
        if (operation != '0') {
            currentBuffer.setText(value.toString() + " " + operation);
        } else {
            currentBuffer.setText("");
        }

        findViewById(R.id.d0).setOnClickListener(this);
        findViewById(R.id.d1).setOnClickListener(this);
        findViewById(R.id.d2).setOnClickListener(this);
        findViewById(R.id.d3).setOnClickListener(this);
        findViewById(R.id.d4).setOnClickListener(this);
        findViewById(R.id.d5).setOnClickListener(this);
        findViewById(R.id.d6).setOnClickListener(this);
        findViewById(R.id.d7).setOnClickListener(this);
        findViewById(R.id.d8).setOnClickListener(this);
        findViewById(R.id.d9).setOnClickListener(this);
        findViewById(R.id.div).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.sub).setOnClickListener(this);
        findViewById(R.id.mul).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
        findViewById(R.id.equal).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.clear):
                currentResult = BigDecimal.ZERO;
                resultText.setText(currentResult.toString());
                break;
            case (R.id.add):
                doOperation('+');
                break;
            case (R.id.sub):
                doOperation('-');
                break;
            case (R.id.mul):
                doOperation('*');
                break;
            case (R.id.div):
                doOperation('/');
                break;
            case (R.id.equal):
                makeResult();
                break;

            case (R.id.d0):
                addNumber(0);
                break;
            case (R.id.d1):
                addNumber(1);
                break;
            case (R.id.d2):
                addNumber(2);
                break;
            case (R.id.d3):
                addNumber(3);
                break;
            case (R.id.d4):
                addNumber(4);
                break;
            case (R.id.d5):
                addNumber(5);
                break;
            case (R.id.d6):
                addNumber(6);
                break;
            case (R.id.d7):
                addNumber(7);
                break;
            case (R.id.d8):
                addNumber(8);
                break;
            case (R.id.d9):
                addNumber(9);
                break;

        }
    }

    private void addNumber(int digit) {
        currentResult = (currentResult.multiply(BigDecimal.valueOf(10))).add(BigDecimal.valueOf(digit));
        resultText.setText(currentResult.toString());
    }

    private void doOperation(char opr) {
        operation = opr;
        value = currentResult;
        currentResult = BigDecimal.ZERO;
        currentBuffer.setText(value.toString() + " " + operation);
        resultText.setText(currentResult.toString());
    }

    private void makeResult() {

        if (operation != '0') {

            switch (operation) {
                case '+':
                    value = value.add(currentResult);
                    break;
                case '-':
                    value = value.subtract(currentResult);
                    break;
                case '*':
                    value = value.multiply(currentResult);
                    break;
                case '/':

                    try {

                        value = value.divide(currentResult);
                    } catch (ArithmeticException e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "И зачем ты это делаешь?", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                        resultText.setText("");
                        operation = '0';
                        currentBuffer.setText("");
                        currentResult = BigDecimal.ZERO;
                    }
                    break;
            }
            resultText.setText(value.toString());
            operation = '0';
            currentBuffer.setText("");
            currentResult = value;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putChar("operation", operation);
        outState.putString("currentResult", currentResult.toString());
        outState.putString("value", value.toString());
    }
}
package ru.ifmo.android_2016.calc;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by Alex on 19.10.16.
 */

public final class CalculatorActivity extends Activity {


    //Init textview
    public TextView resultText;
    public TextView currentBuffer;

    public BigDecimal currentResult = BigDecimal.ZERO;
    public BigDecimal value = BigDecimal.ZERO;
    public char operation = '0';

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        resultText = (TextView) findViewById(R.id.result);
        currentBuffer = (TextView) findViewById(R.id.resultOperator);

        if (savedInstanceState != null) {
            currentResult = BigDecimal.valueOf(Double.parseDouble(savedInstanceState.getString("currentResult","0")));
            value = BigDecimal.valueOf(Double.parseDouble(savedInstanceState.getString("value", "0")));
            operation = savedInstanceState.getChar("operation", '0');
        } else {
            currentResult = BigDecimal.ZERO;
            value = BigDecimal.ZERO;
            operation = '0';
        }

        resultText.setText(currentResult.toString());
        resultText.setMovementMethod(new ScrollingMovementMethod());

        if (operation != '0') {
            currentBuffer.setText(value.toString() + " " + operation);
        } else {
            currentBuffer.setText("");
        }

        findViewById(R.id.d0).setOnClickListener((v) -> addNumber(0));
        findViewById(R.id.d1).setOnClickListener((v) -> addNumber(1));
        findViewById(R.id.d2).setOnClickListener((v) -> addNumber(2));
        findViewById(R.id.d3).setOnClickListener((v) -> addNumber(3));
        findViewById(R.id.d4).setOnClickListener((v) -> addNumber(4));
        findViewById(R.id.d5).setOnClickListener((v) -> addNumber(5));
        findViewById(R.id.d6).setOnClickListener((v) -> addNumber(6));
        findViewById(R.id.d7).setOnClickListener((v) -> addNumber(7));
        findViewById(R.id.d8).setOnClickListener((v) -> addNumber(8));
        findViewById(R.id.d9).setOnClickListener((v) -> addNumber(9));
        findViewById(R.id.div).setOnClickListener((v) -> doOperation('/'));
        findViewById(R.id.add).setOnClickListener((v) -> doOperation('+'));
        findViewById(R.id.sub).setOnClickListener((v) -> doOperation('-'));
        findViewById(R.id.mul).setOnClickListener((v) -> doOperation('*'));
        findViewById(R.id.clear).setOnClickListener((v) -> {
            currentResult = BigDecimal.ZERO;
            resultText.setText(currentResult.toString());});

        findViewById(R.id.equal).setOnClickListener((v) ->  makeResult());



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
            Log.d("calc", currentResult.toString());
            Log.d("calc", value.toString());
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

                        value = value.divide(currentResult, 10, BigDecimal.ROUND_HALF_EVEN);
                    } catch (ArithmeticException e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "И зачем ты это делаешь?", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();

                        operation = '0';
                        currentBuffer.setText("");
                        currentResult = BigDecimal.ZERO;
                        resultText.setText(currentResult.toString());
                        return;
                    }
                    break;
            }
            Log.d("calc", value.toString());
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
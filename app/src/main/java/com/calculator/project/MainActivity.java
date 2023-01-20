package com.calculator.project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private enum OPERATOR {
        PLUS , SUBTRACT , MULTIPLY , DIVIDE , EQUAL , MOD
    }
    private String currentNumber;
    private String stringNumberAtLeft;
    private String stringNumberAtRight;
    private OPERATOR currentOperator;
    private int calculationsResult;
    private String calculationsString;
    TextView txtCalculations;
    TextView txtResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentNumber = "";
        calculationsResult = 0;
        calculationsString = "";

        txtCalculations = findViewById(R.id.txtCalculations);
        txtResults = findViewById(R.id.txtResults);
        findViewById(R.id.buttonClr).setOnClickListener(this);
        findViewById(R.id.buttonDel).setOnClickListener(this);
        findViewById(R.id.buttonMod).setOnClickListener(this);
        findViewById(R.id.buttonDiv).setOnClickListener(this);
        findViewById(R.id.buttonMul).setOnClickListener(this);
        findViewById(R.id.buttonSum).setOnClickListener(this);
        findViewById(R.id.buttonDif).setOnClickListener(this);
        findViewById(R.id.buttonDot).setOnClickListener(this);
        findViewById(R.id.buttonEql).setOnClickListener(this);
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClr:
                clearTapped();
                break;
            case R.id.buttonDel:
                deleteTapped();
                break;
            case R.id.buttonMod:
                operatorIsTapped(OPERATOR.MOD);
                calculationsString = calculationsString + " % ";
                break;
            case R.id.buttonDiv:
                operatorIsTapped(OPERATOR.DIVIDE);
                calculationsString = calculationsString + " / ";
                break;
            case R.id.button7:
                numberIsTapped(7);
                break;
            case R.id.button8:
                numberIsTapped(8);
                break;
            case R.id.button9:
                numberIsTapped(9);
                break;
            case R.id.buttonMul:
                operatorIsTapped(OPERATOR.MULTIPLY);
                calculationsString = calculationsString + " * ";
                break;
            case R.id.button4:
                numberIsTapped(4);
                break;
            case R.id.button5:
                numberIsTapped(5);
                break;
            case R.id.button6:
                numberIsTapped(6);
                break;
            case R.id.buttonDif:
                operatorIsTapped(OPERATOR.SUBTRACT);
                calculationsString = calculationsString + " - ";
                break;
            case R.id.button1:
                numberIsTapped(1);
                break;
            case R.id.button2:
                numberIsTapped(2);
                break;
            case R.id.button3:
                numberIsTapped(3);
                break;
            case R.id.buttonSum:
                operatorIsTapped(OPERATOR.PLUS);
                calculationsString = calculationsString + " + ";
                break;
            case R.id.button0:
                numberIsTapped(0);
                break;
            case R.id.buttonDot:
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
                break;
            case R.id.buttonEql:
                operatorIsTapped(OPERATOR.EQUAL);
                break;
        }
        txtCalculations.setText(calculationsString);
    }
    private void numberIsTapped(int tappedNubmer) {
        currentNumber = currentNumber + String.valueOf(tappedNubmer);
        txtResults.setText(currentNumber);
        calculationsString = currentNumber;
        txtCalculations.setText(calculationsString);
    }
    private void operatorIsTapped(OPERATOR tappedOperator) {
        if(currentOperator != null) {
            if(currentNumber != "") {
                stringNumberAtRight = currentNumber;
                currentNumber = "";

                switch (currentOperator) {
                    case PLUS:
                        calculationsResult = Integer.parseInt(stringNumberAtLeft) + Integer.parseInt(stringNumberAtRight);
                        break;
                    case SUBTRACT:
                        calculationsResult = Integer.parseInt(stringNumberAtLeft) - Integer.parseInt(stringNumberAtRight);
                        break;
                    case MULTIPLY:
                        calculationsResult = Integer.parseInt(stringNumberAtLeft) * Integer.parseInt(stringNumberAtRight);
                        break;
                    case DIVIDE:
                        calculationsResult = Integer.parseInt(stringNumberAtLeft) / Integer.parseInt(stringNumberAtRight);
                        break;
                    case MOD:
                        calculationsResult = Integer.parseInt(stringNumberAtLeft) % Integer.parseInt(stringNumberAtRight);
                        break;
                }
                stringNumberAtLeft = String.valueOf(calculationsResult);
                txtResults.setText(stringNumberAtLeft);
                calculationsString = stringNumberAtLeft;
            }
        } else {
            stringNumberAtLeft = currentNumber;
            currentNumber = "";
        }
        currentOperator = tappedOperator;
    }
    private void clearTapped() {
        stringNumberAtLeft = "";
        stringNumberAtRight = "";
        calculationsResult = 0;
        currentOperator = null;
        currentNumber = "";
        txtResults.setText("0");
        calculationsString = "0";
    }
    private void deleteTapped() {
        if(calculationsString != "" || currentNumber != "") {
            calculationsString = calculationsString.substring(0, currentNumber.length() - 1);
            currentNumber = currentNumber.substring(0,currentNumber.length() - 1);
            if(currentNumber.length() == 0)
                txtResults.setText("0");
            else
                txtResults.setText(currentNumber);

            txtCalculations.setText(calculationsString);
        }
    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
}

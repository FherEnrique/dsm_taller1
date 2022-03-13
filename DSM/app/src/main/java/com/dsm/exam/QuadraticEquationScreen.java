package com.dsm.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuadraticEquationScreen extends AppCompatActivity {
    TextView totalTextView;
    EditText variableA;
    EditText variableB;
    EditText variableC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadratic_equation_screen);
        totalTextView = findViewById(R.id.resultTextView);
        variableA = findViewById(R.id.variableA);
        variableB = findViewById(R.id.variableB);
        variableC = findViewById(R.id.variableC);
    }

    public void onResetFieldsClick(View view) {
        variableA.setText("");
        variableB.setText("");
        variableC.setText("");
        totalTextView.setText(R.string.x1_0_x2_0_default_label);
    }

    public void onResolveEquationClick(View view) {
        try {
            resolveEquation(
                    variableA.getText().toString(),
                    variableB.getText().toString(),
                    variableC.getText().toString(),
                    view
            );
        } catch (Exception exception) {
            Toast.makeText(
                    this,
                    "Ocurrió un problema, verifica tus datos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void resolveEquation(String varA, String varB, String varC, View view) {
        double a = Double.parseDouble(varA);
        double b = Double.parseDouble(varB);
        double c = Double.parseDouble(varC);
        double sqrtValue = resolveSqrtValue(a, b, c);

        if (sqrtValue >= 0) {
            double positive = resolvePositive(sqrtValue, a, b);
            double negative = resolveNegative(sqrtValue, a, b);
            String total = "X1: " + positive + ", X2: " + negative;
            totalTextView.setText(total);
        } else {
            Toast.makeText(
                    this,
                    "Raíz cuadrada negativa, no se puede resolver.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private double resolveSqrtValue(double a, double b, double c) {
        return Math.pow(b, 2) - (4 * a * c);
    }

    private double resolvePositive(double sqrtValue, double a, double b) {
        return ((-b) + Math.sqrt(sqrtValue)) / (2 * a);
    }

    private double resolveNegative(double sqrtValue, double a, double b) {
        return ((-b) - Math.sqrt(sqrtValue)) / (2 * a);
    }
}

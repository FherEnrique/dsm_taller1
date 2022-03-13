package com.dsm.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dsm.exam.model.Auth;
import com.dsm.exam.service.AuthService;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    EditText emailField;
    EditText passwordField;
    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        AuthService auth = new AuthService(this);
        auth.addDefaultUser();

        emailField = findViewById(R.id.emailTextField);
        passwordField = findViewById(R.id.passwordTextField);
        authService = auth;
    }

    public void onLoginClick(View view) {
        String email = String.valueOf(emailField.getText());
        String password = String.valueOf(passwordField.getText());

        try {
            Auth authUser = authService.getAuthUser(email, password);

            if (authUser != null) {
                Snackbar.make(view, "Bienvenido " + authUser.getName(), 3000).show();

                Intent intent = new Intent(this, DashboardScreen.class);
                startActivity(intent);
            }
        } catch (Exception exception) {
            Toast.makeText(this, "Credenciales no validas", Toast.LENGTH_LONG).show();
        }
    }
}

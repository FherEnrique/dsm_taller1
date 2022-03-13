package com.dsm.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dsm.exam.adapter.DashboardRecyclerView;
import com.dsm.exam.model.MenuOption;

import java.util.ArrayList;
import java.util.List;

public class DashboardScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);

        RecyclerView recyclerView = findViewById(R.id.menuOptions);
        List<MenuOption> options = new ArrayList();

        options.add(new MenuOption(1, "Punto 1", "Ecuación Cuadrática"));
        options.add(new MenuOption(2, "Punto 2", "Votos"));
        options.add(new MenuOption(3, "Punto 3", "Pago de sueldo"));

        DashboardRecyclerView adapter = new DashboardRecyclerView(options, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

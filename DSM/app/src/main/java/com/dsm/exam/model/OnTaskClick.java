package com.dsm.exam.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dsm.exam.QuadraticEquationScreen;
import com.dsm.exam.SalaryScreen;
import com.dsm.exam.VotesScreen;

public class OnTaskClick implements View.OnClickListener {
    private final int taskId;
    private final Context context;

    public OnTaskClick(int taskId, Context context) {
        this.taskId = taskId;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (taskId) {
            case 1: {
                Intent intent = new Intent(context, QuadraticEquationScreen.class);
                context.startActivity(intent);
                break;
            }
            case 2: {
                Intent intent = new Intent(context, VotesScreen.class);
                context.startActivity(intent);
                break;
            }
            case 3: {
                Intent intent = new Intent(context, SalaryScreen.class);
                context.startActivity(intent);
                break;
            }
        }
    }
}

package com.dsm.exam.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsm.exam.R;
import com.dsm.exam.model.Employee;
import com.dsm.exam.model.EmployeeSalary;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSalariesAdapter extends RecyclerView.Adapter<EmployeeSalariesAdapter.ViewHolder> {
    List<EmployeeSalary> salaries = new ArrayList<>();

    public EmployeeSalariesAdapter(List<EmployeeSalary> salaries) {
        this.salaries.addAll(salaries);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_salary_letter, parent, false);
        return new EmployeeSalariesAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmployeeSalary employeeSalary = salaries.get(position);
        Employee employee = employeeSalary.getEmployee();
        String title = employee.getName() + " (" + employee.getRole() + ")";
        @SuppressLint("DefaultLocale")
        String salaryTotal = String.format("%.02f",employeeSalary.getTotalSalary());
        @SuppressLint("DefaultLocale")
        String salary = String.format("%.02f", employeeSalary.getSalary());
        holder.employeeNameTextView.setText(title);
        holder.salaryTotalTextView.setText("Salario líquido: $" + salaryTotal);
        holder.salaryWithDiscountsTextView.setText("Salario: $" + salary);
    }

    @Override
    public int getItemCount() {
        return salaries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView employeeNameTextView;
        private final TextView salaryTotalTextView;
        private final TextView salaryWithDiscountsTextView;

        public ViewHolder(View view) {
            super(view);
            employeeNameTextView = view.findViewById(R.id.letterEmployeeName);
            salaryTotalTextView = view.findViewById(R.id.letterEmployeeSalaryTotal);
            salaryWithDiscountsTextView = view.findViewById(R.id.letterEmployeeSalaryWithDiscounts);
        }
    }
}

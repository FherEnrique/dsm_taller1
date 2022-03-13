package com.dsm.exam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dsm.exam.adapter.EmployeeSalariesAdapter;
import com.dsm.exam.model.Employee;
import com.dsm.exam.model.EmployeeSalary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SalaryScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    List<Employee> employees = new ArrayList<>();
    List<String> employeeRoles = new ArrayList<>();
    String employeeRoleSelected;
    TextView employeesCountTextView;
    EditText employeeNameEditText;
    EditText employeeHoursEditText;
    Button addEmployeesBtn;
    Button loadEmployeesDataBtn;
    TextView highestSalaryTextView;
    TextView lowestSalaryTextView;
    TextView salariesAbove300TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_screen);
        onLoadEmployeeDataCollector();
        onLoadEmployeeDataCalculator();
    }

    @SuppressLint("SetTextI18n")
    public void onAddNewEmployeeInfoClicks(View view) {
        String name = String.valueOf(employeeNameEditText.getText());
        String role = employeeRoleSelected;
        int hours = Integer.parseInt(String.valueOf(employeeHoursEditText.getText()));
        int id = employees.size() + 1;

        if (name.trim().length() > 0 && hours > 0) {
            employees.add(new Employee(id, name, role, hours));
            employeesCountTextView.setText("Empleados registrados: " + employees.size());
            Toast.makeText(this, "Empleado registrado", Toast.LENGTH_SHORT).show();
            checkButtonValidations();
            cleanEmployeeFields();
        } else {
            Toast.makeText(this, "Verifica los datos", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onLoadEmployeesData(View view) {
        isCollectorActive(false);
        calculateEmployeeSalary();
    }

    public void onCleanResultsClick(View view) {
        isCollectorActive(true);
        onCleanEmployeesData(view);
    }

    public void onCleanEmployeesData(View view) {
        employeesCountTextView.setText(R.string.no_employees_registered_label);
        employees.clear();
        cleanEmployeeFields();
        checkButtonValidations();
        setSpinnerConfiguration();
    }

    private void isCollectorActive(boolean flag) {
        LinearLayout collectorLayout = findViewById(R.id.employeeDataCollectorLayout);
        LinearLayout resultsLayout = findViewById(R.id.employeeDataResultsLayout);
        collectorLayout.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
        resultsLayout.setVisibility(flag ? View.INVISIBLE : View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void calculateEmployeeSalary() {
        boolean canBonusBeApplied = !checkIfBonusCannotBeApplied();
        List<EmployeeSalary> employeeSalaryList = new ArrayList<>();
        for (Employee employee : employees) {
            int hours = employee.getHours();
            double payment = generatePaymentTotal(hours);
            double paymentWithDiscounts = applyNationalDiscounts(payment);
            if (canBonusBeApplied) {
                paymentWithDiscounts += applyBonusByRole(payment, employee.getRole());
            }
            employeeSalaryList.add(new EmployeeSalary(employee, payment, paymentWithDiscounts));
        }

        if (!canBonusBeApplied) {
            Toast.makeText(this, "NO HAY BONO", Toast.LENGTH_SHORT).show();
        }

        RecyclerView recyclerView = findViewById(R.id.salary_list);
        EmployeeSalariesAdapter adapter = new EmployeeSalariesAdapter(employeeSalaryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calculateVariations(employeeSalaryList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void calculateVariations(List<EmployeeSalary> employeeSalary) {
        List<String> above300Names = new ArrayList<>();
        EmployeeSalary highest = whoHaveTheHighestSalary(employeeSalary);
        EmployeeSalary lowest = whoHaveTheLowestSalary(employeeSalary);
        List<EmployeeSalary> above300 = employeeSalary.stream().filter(
                entity -> entity.getSalary() > 300
        ).collect(Collectors.toList());

        for (EmployeeSalary entity : above300) {
            above300Names.add(entity.getEmployee().getName());
        }

        String names = "Salarios arriba de $300: " + String.join(", ", above300Names);
        if (highest != null) {
            highestSalaryTextView.setText(highest.getEmployee().getName() + " tiene mayor salario.");
        }
        if (lowest != null) {
            lowestSalaryTextView.setText(lowest.getEmployee().getName() + " tiene menor salario.");
        }
        salariesAbove300TextView.setText(names);
    }

    private EmployeeSalary whoHaveTheHighestSalary(List<EmployeeSalary> employeeSalary) {
        EmployeeSalary employeeSalary1 = employeeSalary.get(0);
        EmployeeSalary employeeSalary2 = employeeSalary.get(1);
        EmployeeSalary employeeSalary3 = employeeSalary.get(2);

        if (employeeSalary1 != null && employeeSalary2 != null && employeeSalary3 != null) {
            if (
                    employeeSalary1.getSalary() > employeeSalary2.getSalary() &&
                            employeeSalary1.getSalary() > employeeSalary3.getSalary()) {
                return employeeSalary1;
            } else if (employeeSalary2.getSalary() > employeeSalary3.getSalary()) {
                return employeeSalary2;
            }

            return employeeSalary3;
        }
        return null;
    }

    private EmployeeSalary whoHaveTheLowestSalary(List<EmployeeSalary> employeeSalary) {
        EmployeeSalary employeeSalary1 = employeeSalary.get(0);
        EmployeeSalary employeeSalary2 = employeeSalary.get(1);
        EmployeeSalary employeeSalary3 = employeeSalary.get(2);

        if (employeeSalary1 != null && employeeSalary2 != null && employeeSalary3 != null) {
            if (employeeSalary1.getSalary() < employeeSalary2.getSalary() &&
                    employeeSalary1.getSalary() < employeeSalary3.getSalary()) {
                return employeeSalary1;
            } else if (employeeSalary2.getSalary() < employeeSalary3.getSalary()) {
                return employeeSalary2;
            }

            return employeeSalary3;
        }

        return null;
    }

    private boolean checkIfBonusCannotBeApplied() {
        boolean flag = true;

        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (flag) {
                if (i != 0 && employee.getRole().contains("Gerente")) {
                    flag = false;
                }
                if (i != 1 && employee.getRole().contains("Asistente")) {
                    flag = false;
                }
                if (i != 2 && employee.getRole().contains("Secretaria")) {
                    flag = false;
                }
            } else break;
        }

        return flag;
    }

    private double generatePaymentTotal(int hours) {
        double COMMON_HOUR_RATE = 9.75;
        double EXTRA_HOUR_RATE = 11.50;
        int MAX_ALLOWED_COMMON_HOURS = 160;
        double paymentValue = 0;

        if (hours > MAX_ALLOWED_COMMON_HOURS) {
            int leftOverHours = hours - MAX_ALLOWED_COMMON_HOURS;
            paymentValue += COMMON_HOUR_RATE * MAX_ALLOWED_COMMON_HOURS;
            paymentValue += leftOverHours * EXTRA_HOUR_RATE;
        } else {
            paymentValue = COMMON_HOUR_RATE * hours;
        }

        return paymentValue;
    }

    private double applyBonusByRole(double paymentValue, String role) {
        double paymentWithBonus = paymentValue;
        if (role.contains(employeeRoles.get(0))) {
            paymentWithBonus *= 0.1;
        } else if (role.contains(employeeRoles.get(1))) {
            paymentWithBonus *= 0.05;
        } else if (role.contains(employeeRoles.get(2))) {
            paymentWithBonus *= 0.03;
        } else if (role.contains(employeeRoles.get(3))) {
            paymentWithBonus *= 0.02;
        }

        return paymentWithBonus;
    }

    private double applyNationalDiscounts(double paymentValue) {
        double ISSS = 0.0525;
        double AFP = 0.0688;
        double RENT = 0.1;
        paymentValue *= 1 - ISSS;
        paymentValue *= 1 - AFP;
        paymentValue *= 1 - RENT;
        return paymentValue;
    }

    private void cleanEmployeeFields() {
        employeeNameEditText.setText("");
        employeeHoursEditText.setText("0");
        employeeRoleSelected = employeeRoles.get(0);
    }

    private void checkButtonValidations() {
        if (employees.size() == 3) {
            addEmployeesBtn.setEnabled(false);
            loadEmployeesDataBtn.setEnabled(true);
        } else {
            addEmployeesBtn.setEnabled(true);
            loadEmployeesDataBtn.setEnabled(false);
        }
    }

    private void onLoadEmployeeDataCalculator() {
        highestSalaryTextView = findViewById(R.id.highestSalary);
        lowestSalaryTextView = findViewById(R.id.lowestSalary);
        salariesAbove300TextView = findViewById(R.id.salaryAbove300);
    }

    private void onLoadEmployeeDataCollector() {
        setSpinnerConfiguration();
        employeesCountTextView = findViewById(R.id.employeesCount);
        employeeNameEditText = findViewById(R.id.employeeFullName);
        employeeHoursEditText = findViewById(R.id.employeeHoursCount);
        addEmployeesBtn = findViewById(R.id.addEmployeeBtn);
        loadEmployeesDataBtn = findViewById(R.id.loadEmployeesData);
        employeeRoles.add("Gerente");
        employeeRoles.add("Asistente");
        employeeRoles.add("Secretaria");
        employeeRoles.add("Operaciones");
        employeeRoleSelected = employeeRoles.get(0);
    }

    private void setSpinnerConfiguration() {
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.employee_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        employeeRoleSelected = employeeRoles.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Necesitas escoger un rol", Toast.LENGTH_SHORT).show();
    }
}

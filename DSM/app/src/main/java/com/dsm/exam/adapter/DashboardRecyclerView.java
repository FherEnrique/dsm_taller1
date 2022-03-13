package com.dsm.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsm.exam.R;
import com.dsm.exam.model.MenuOption;
import com.dsm.exam.model.OnTaskClick;

import java.util.ArrayList;
import java.util.List;

public class DashboardRecyclerView extends RecyclerView.Adapter<DashboardRecyclerView.ViewHolder> {
    List<MenuOption> options = new ArrayList<>();
    Context context;

    public DashboardRecyclerView(List<MenuOption> options, Context context) {
        this.options.addAll(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuOption option = options.get(position);
        holder.getOptionTitleText().setText(option.getTitle());
        holder.getOptionNameText().setText(option.getName());
        holder.getVisitOption().setOnClickListener(new OnTaskClick(option.getId(), context));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView optionTitleText;
        private final TextView optionNameText;
        private final Button visitOption;

        public ViewHolder(View view) {
            super(view);

            optionTitleText = view.findViewById(R.id.optionTitle);
            optionNameText = view.findViewById(R.id.optionName);
            visitOption = view.findViewById(R.id.visitBtn);
        }

        public TextView getOptionTitleText() { return optionTitleText; }
        public TextView getOptionNameText() { return optionNameText; }
        public Button getVisitOption() { return visitOption; }
    }
}

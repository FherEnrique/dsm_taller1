package com.dsm.exam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dsm.exam.model.CandidateCount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VotesScreen extends AppCompatActivity {
    List<String> candidates = new ArrayList();
    List<String> votes = new ArrayList();
    TextView votesResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votes_screen);
        votesResults = findViewById(R.id.votesResults);
        candidates.add("C1");
        candidates.add("C2");
        candidates.add("C3");
        candidates.add("C4");
    }

    public void onCleanVotesClick(View view) {
        votes.clear();
        votesResults.setText("");
    }

    public void onProceedVoteClick(View view) {
        Button button = findViewById(view.getId());
        votes.add(String.valueOf(button.getText()));
        Toast.makeText(this, "Voto enviado", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCountVotesClick(View view) {
        List<CandidateCount> count = new ArrayList<>();
        for (String candidate : candidates) {
            List<String> candidateVotes = votes.stream().filter(
                    vote -> vote.contains(candidate)
            ).collect(Collectors.toList());

            int votesCount = candidateVotes.size();
            double percent = (votesCount > 0 && votes.size() > 0)
                    ? ((double) votesCount / votes.size()) * 100
                    : 0;
            count.add(new CandidateCount(candidate, votesCount, percent));
        }
        prepareResults(count);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prepareResults(List<CandidateCount> counts) {
        List<String> textResult = new ArrayList<>();

        for (CandidateCount count : counts) {
            String percent = String.format("%.02f", count.getPercent());
            textResult.add(count.getCandidate() + " tiene el " + percent + "%\n");
        }

        votesResults.setEnabled(true);
        votesResults.setText(String.join("", textResult));
    }
}

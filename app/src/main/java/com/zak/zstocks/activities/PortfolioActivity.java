package com.zak.zstocks.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.zak.zstocks.adapter.PortfolioAdapter;
import com.zak.zstocks.model.PortfolioItem;
import com.zak.zstocks.R;

import java.util.ArrayList;
import java.util.List;

public class PortfolioActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        recyclerView = findViewById(R.id.portfolioRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPortfolio(); // 🔥 NEW METHOD
    }

    private void loadPortfolio() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users")
                .document(userId)
                .collection("portfolio")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<PortfolioItem> list = new ArrayList<>();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        PortfolioItem item = doc.toObject(PortfolioItem.class);
                        if (item != null) {
                            list.add(item);
                        }
                    }

                    recyclerView.setAdapter(new PortfolioAdapter(this, list));
                });
    }

}

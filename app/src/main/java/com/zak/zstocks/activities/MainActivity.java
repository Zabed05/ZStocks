package com.zak.zstocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zak.zstocks.R;
import com.zak.zstocks.adapter.StockAdapter;
import com.zak.zstocks.model.Stock;
import com.zak.zstocks.model.User;
import com.zak.zstocks.utils.FakeStockService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView userName, userBalance;
    Button logoutBtn, portfolioBtn;

    ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Safety check (if user not logged in)
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        userName = findViewById(R.id.userName);
        userBalance = findViewById(R.id.userBalance);
        logoutBtn = findViewById(R.id.logoutBtn);
        portfolioBtn = findViewById(R.id.portfolioBtn);
        profileIcon = findViewById(R.id.profileIcon);

        // Load Stocks
        List<Stock> stocks = FakeStockService.getStocks();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StockAdapter(this, stocks));

        // Load User Profile
        loadUserData();

        // User Profile
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Portfolio Button
        portfolioBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, PortfolioActivity.class));
        });

        // Logout
        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    // 🔄 Refresh when returning from buy/sell screen
    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }

    // 👤 Load user from Firebase
    private void loadUserData() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(doc -> {

                    if (doc.exists()) {

                        User user = doc.toObject(User.class);

                        if (user != null) {
                            userName.setText("👤 " + user.getName());
                            userBalance.setText("Balance: ₹" + user.getBalance());
                        }

                    } else {
                        Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
                });
    }
}

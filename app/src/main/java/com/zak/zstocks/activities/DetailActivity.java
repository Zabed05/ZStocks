package com.zak.zstocks.activities;


import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zak.zstocks.R;
import com.zak.zstocks.utils.PortfolioManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.*;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    String stockName;
    int stockPrice;

    FirebaseFirestore db;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        stockName = getIntent().getStringExtra("name");
        stockPrice = getIntent().getIntExtra("price", 100);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        TextView name = findViewById(R.id.detailName);
        name.setText(stockName);

        TextView price = findViewById(R.id.detailPrice);
        price.setText(String.valueOf(stockPrice));

        setupChart();

        findViewById(R.id.buyBtn).setOnClickListener(v -> buyStock());
        findViewById(R.id.sellBtn).setOnClickListener(v -> sellStock());
    }

    // 📈 Chart Setup
    private void setupChart() {
        LineChart chart = findViewById(R.id.chart);

        chart.clear();

        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0f, 100f));
        entries.add(new Entry(1f, 105f));
        entries.add(new Entry(2f, 102f));
        entries.add(new Entry(3f, 110f));
        entries.add(new Entry(4f, 108f));
        entries.add(new Entry(5f, 115f));
        entries.add(new Entry(6f, 120f));
        entries.add(new Entry(7f, 118f));
        entries.add(new Entry(8f, 125f));
        entries.add(new Entry(9f, 130f));
        entries.add(new Entry(10f, 128f));
        entries.add(new Entry(11f, 135f));
        entries.add(new Entry(12f, 140f));
        entries.add(new Entry(13f, 138f));
        entries.add(new Entry(14f, 145f));

        LineDataSet dataSet = new LineDataSet(entries, "Stock Price");
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }


    // 💰 BUY STOCK
    private void buyStock() {

        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(doc -> {

                    if (doc.exists()) {
                        Long balanceLong = doc.getLong("balance");

                        if (balanceLong != null) {
                            int balance = balanceLong.intValue();

                            if (balance >= stockPrice) {

                                int newBalance = balance - stockPrice;

                                // Update balance
                                db.collection("users")
                                        .document(userId)
                                        .update("balance", newBalance);

                                // Add to portfolio
                                PortfolioManager.buyStock(stockName);

                                Toast.makeText(this, "Bought " + stockName, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(this, "Not enough balance", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    // 💵 SELL STOCK
    private void sellStock() {

        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(doc -> {

                    if (doc.exists()) {
                        Long balanceLong = doc.getLong("balance");

                        if (balanceLong != null) {
                            int balance = balanceLong.intValue();

                            int newBalance = balance + stockPrice;

                            // Update balance
                            db.collection("users")
                                    .document(userId)
                                    .update("balance", newBalance);

                            // Remove from portfolio
                            PortfolioManager.sellStock(stockName);

                            Toast.makeText(this, "Sold " + stockName, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

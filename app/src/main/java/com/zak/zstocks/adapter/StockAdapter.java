package com.zak.zstocks.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.*;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zak.zstocks.R;
import com.zak.zstocks.model.Stock;
import com.zak.zstocks.activities.DetailActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.*;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private Context context;
    private List<Stock> list;

    public StockAdapter(Context context, List<Stock> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Stock stock = list.get(position);

        holder.name.setText(stock.getName());
        holder.price.setText("₹ " + stock.getPrice());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("name", stock.getName());
            intent.putExtra("price", stock.getPrice());
            context.startActivity(intent);
        });

        // ==========================
        // 📈 MINI CHART (UPGRADED)
        // ==========================

        ArrayList<Entry> entries = new ArrayList<>();

        // You can later replace this with REAL stock history from DB/API
        float base = (float) stock.getPrice();

        entries.add(new Entry(0f, base - 10));
        entries.add(new Entry(1f, base + 5));
        entries.add(new Entry(2f, base - 3));
        entries.add(new Entry(3f, base + 12));
        entries.add(new Entry(4f, base + 8));

        LineDataSet dataSet = new LineDataSet(entries, "");

        // 🎯 Clean sparkline style
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(2.5f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);

        // 🎯 Decide GREEN or RED based on trend
        float start = entries.get(0).getY();
        float end = entries.get(entries.size() - 1).getY();

        int lineColor;
        if (end >= start) {
            lineColor = Color.parseColor("#00E676"); // green
        } else {
            lineColor = Color.parseColor("#FF5252"); // red
        }

        dataSet.setColor(lineColor);

        // 🎯 Fill under graph (soft trading glow)
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(60);

        // optional gradient-like effect using solid fill
        dataSet.setFillColor(lineColor);

        // ==========================
        // DATA SET TO CHART
        // ==========================

        LineData lineData = new LineData(dataSet);
        holder.miniChart.setData(lineData);

        // ==========================
        // CLEAN CHART UI
        // ==========================

        holder.miniChart.getDescription().setEnabled(false);
        holder.miniChart.getLegend().setEnabled(false);

        holder.miniChart.getXAxis().setEnabled(false);
        holder.miniChart.getAxisLeft().setEnabled(false);
        holder.miniChart.getAxisRight().setEnabled(false);

        holder.miniChart.setTouchEnabled(false);
        holder.miniChart.setDragEnabled(false);
        holder.miniChart.setScaleEnabled(false);

        holder.miniChart.setViewPortOffsets(0, 0, 0, 0);

        // smooth animation
        holder.miniChart.animateX(600);

        holder.miniChart.invalidate();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
        LineChart miniChart;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.stockName);
            price = itemView.findViewById(R.id.stockPrice);
            miniChart = itemView.findViewById(R.id.miniChart);
        }
    }
}

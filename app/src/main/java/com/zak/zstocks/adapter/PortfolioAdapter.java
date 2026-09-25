package com.zak.zstocks.adapter;

import android.content.Context;
import android.view.*;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zak.zstocks.R;
import com.zak.zstocks.model.PortfolioItem;

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {

    private Context context;
    private List<PortfolioItem> list;

    public PortfolioAdapter(Context context, List<PortfolioItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_portfolio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PortfolioItem item = list.get(position);

        holder.name.setText(item.getStockName());
        holder.qty.setText("Qty: " + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, qty;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.portfolioName);
            qty = itemView.findViewById(R.id.portfolioQty);
        }
    }
}

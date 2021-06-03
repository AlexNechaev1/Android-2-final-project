package com.example.android_2_final_project.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_2_final_project.ExploreCellData;
import com.example.android_2_final_project.R;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private List<ExploreCellData> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RecyclerViewAdapter(Context context, List<ExploreCellData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_explorer_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExploreCellData data = mData.get(position);

        holder.title.setText(data.getTitle());
        holder.firstDescription.setText(data.getFirstDescription());
        holder.firstDescription.setText(data.getSecondDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView title;
        TextView firstDescription;
        TextView secondDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.explore_cell_imageView);
            title = itemView.findViewById(R.id.explore_cell_title);
            firstDescription = itemView.findViewById(R.id.first_description_text_view);
            secondDescription = itemView.findViewById(R.id.second_description_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("markomarko", "onClick: " + getAdapterPosition());
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    public ExploreCellData getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}
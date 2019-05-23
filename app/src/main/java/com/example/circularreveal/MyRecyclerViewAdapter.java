package com.example.circularreveal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ItemModel> itemModels;
    private rvItemClickedListener mListener;

    public MyRecyclerViewAdapter(Context context, ArrayList<ItemModel> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public interface rvItemClickedListener {
        void itemClicked(int position, float[] coordinates);
    }

    public void setRVItemClickedListener(rvItemClickedListener mListener) {
        this.mListener = mListener;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView centerOfIcon;
        private RelativeLayout container;
        private TextView title;

        private float[] itemClickedCoordinate = new float[2];

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.files_rv_item_grid_container);
            centerOfIcon = itemView.findViewById(R.id.center_of_icon);
            title = itemView.findViewById(R.id.files_rv_title_grid);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickedCoordinate[0] = container.getX() + (container.getWidth() / 2f);
                    itemClickedCoordinate[1] = container.getY() + (container.getHeight() / 2f);

                    if (mListener != null) {
                        mListener.itemClicked(getAdapterPosition(), itemClickedCoordinate);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemModel currentModel = itemModels.get(position);
        holder.title.setText(currentModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

}

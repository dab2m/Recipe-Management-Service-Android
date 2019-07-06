package com.example.recipemanagementservice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Berk on 16.06.2019.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    ArrayList<Food> mFoodList;
    LayoutInflater inflater;

    public FoodAdapter(Context context, ArrayList<Food> foods) {
        inflater = LayoutInflater.from(context);
        this.mFoodList = foods;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_recipe, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Food selectFood = mFoodList.get(position);
        holder.setData(selectFood, position);
        holder.bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFoodList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView foodName, foodDescription, foodTag;
        ImageView foodImage;
        Button bDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.yemekIsmi);
            foodDescription = (TextView) itemView.findViewById(R.id.yemekAciklamasi);
            foodTag = (TextView) itemView.findViewById(R.id.yemekEtiketleri);
            foodImage = (ImageView) itemView.findViewById(R.id.yemekResmi);
            bDelete = (Button) itemView.findViewById(R.id.bDelete);
        }

        public void setData(Food selectedFood, int position) {
            this.foodName.setText(selectedFood.getFoodName());
            this.foodDescription.setText(selectedFood.getFoodDescription());
            this.foodTag.setText(selectedFood.getFoodTag());
            this.foodImage.setImageResource(selectedFood.getFoodImage());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
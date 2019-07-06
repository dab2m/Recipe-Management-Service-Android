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

import java.util.ArrayList;

/**
 * Created by Berk on 16.06.2019.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    ArrayList<Food> mFoodList;
    LayoutInflater inflater;
    int withDelete; // 1:without delete    2:with delete

    public FoodAdapter(Context context, ArrayList<Food> foods, int withDelete) {
        inflater = LayoutInflater.from(context);
        this.mFoodList = foods;
        this.withDelete = withDelete;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (withDelete == 1) {
            View view = inflater.inflate(R.layout.item_recipe_without_delete, viewGroup, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        } else {
            View view = inflater.inflate(R.layout.item_recipe_with_delete, viewGroup, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Food selectFood = mFoodList.get(position);
        holder.setData(selectFood, position);
        if (withDelete == 2) {
            holder.bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFoodList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
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
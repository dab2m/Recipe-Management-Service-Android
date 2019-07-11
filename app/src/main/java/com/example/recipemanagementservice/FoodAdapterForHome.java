package com.example.recipemanagementservice;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mustafatozluoglu on 11.07.2019
 */
public class FoodAdapterForHome extends BaseAdapter {

    Context context;
    ArrayList<Food> recipeArrayList;
    LayoutInflater layoutInflater;

    public FoodAdapterForHome(Activity activity, ArrayList<Food> recipeArrayList){
        this.context = activity;
        this.recipeArrayList = recipeArrayList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return recipeArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return recipeArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_recipe_without_delete, null);
        TextView recipeName = (TextView) view.findViewById(R.id.yemekIsmi);
        ImageView recipeImage = (ImageView) view.findViewById(R.id.yemekResmi);
        TextView recipeDecription = (TextView) view.findViewById(R.id.yemekAciklamasi);
        TextView recipeTags = (TextView) view.findViewById(R.id.yemekEtiketleri);

        recipeName.setText(recipeArrayList.get(i).getFoodName());
        // TODO yemek resmi ayarlanacak
        recipeDecription.setText(recipeArrayList.get(i).getFoodDescription());
        recipeTags.setText(recipeArrayList.get(i).getFoodTags());

        return view;
    }
}

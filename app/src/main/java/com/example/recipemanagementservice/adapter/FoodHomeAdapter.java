package com.example.recipemanagementservice.adapter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.model.FoodModel;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Berk on 16.06.2019.
 */
public class FoodHomeAdapter extends BaseAdapter {

    Context context;
    ArrayList<FoodModel> recipeArrayList;
    LayoutInflater layoutInflater;
    Button bLike;

    public FoodHomeAdapter(Activity activity, ArrayList<FoodModel> recipeArrayList){
        this.context = activity;
        this.recipeArrayList = recipeArrayList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return recipeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_recipe_without_delete, null);
        TextView recipeName = (TextView) view.findViewById(R.id.yemekIsmi);
        ImageView recipeImage = (ImageView) view.findViewById(R.id.yemekResmi);
        new DownLoadImageTask(recipeImage).execute(recipeArrayList.get(position).getFoodImage());
        TextView recipeDecription = (TextView) view.findViewById(R.id.yemekAciklamasi);
        TextView recipeTags = (TextView) view.findViewById(R.id.yemekEtiketleri);
        bLike = (Button) view.findViewById(R.id.bBegen);
        recipeName.setText(recipeArrayList.get(position).getFoodName());
        recipeDecription.setText(recipeArrayList.get(position).getFoodDescription());
        recipeTags.setText(recipeArrayList.get(position).displayTags());
        return view;
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is;
                if(urlOfImage.contains("no.png")){
                    is = new URL("https://res.cloudinary.com/dewae3den/image/upload/v1563364160/no_anet7u.png").openStream();
                }else{
                    is = new URL(urlOfImage).openStream();
                }
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
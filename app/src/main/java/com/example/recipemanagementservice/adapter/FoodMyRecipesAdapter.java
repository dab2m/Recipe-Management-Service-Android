package com.example.recipemanagementservice.adapter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.activity.MyRecipesActivity;
import com.example.recipemanagementservice.model.FoodModel;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mustafatozluoglu on 11.07.2019
 */
public class FoodMyRecipesAdapter extends BaseAdapter {

    Context context;
    ArrayList<FoodModel> recipeArrayList;
    LayoutInflater layoutInflater;
    Button bDelete;
    private String recipeId;

    public FoodMyRecipesAdapter(Activity activity, ArrayList<FoodModel> recipeArrayList){
        this.context = activity;
        this.recipeArrayList = recipeArrayList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
    }

    public FoodMyRecipesAdapter() {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_recipe_with_delete, null);
        TextView recipeName = (TextView) view.findViewById(R.id.tvYemekIsmi);
        ImageView recipeImage = (ImageView) view.findViewById(R.id.ivYemekResmi);
        new DownLoadImageTask(recipeImage).execute(recipeArrayList.get(position).getFoodImage());
        TextView recipeDecription = (TextView) view.findViewById(R.id.tvYemekAciklamasi);
        TextView recipeTags = (TextView) view.findViewById(R.id.tvYemekEtiketleri);
        bDelete = (Button) view.findViewById(R.id.bDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeId = recipeArrayList.get(position).getFoodId();
                SharedPreferences prefs = context.getSharedPreferences("MyApp", MODE_PRIVATE); // LoginActivity sayfasindan username'i almak icin kullanildi!
                String username = prefs.getString("username", "UNKNOWN");
                deletePost(recipeId, username);
            }
        });
        recipeName.setText(recipeArrayList.get(position).getFoodName());
        recipeDecription.setText(recipeArrayList.get(position).getFoodDescription());
        recipeTags.setText(recipeArrayList.get(position).displayTags());
        return view;
    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
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

    public void deletePost(final String recipeId, final String username) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://recipemanagementservice495.herokuapp.com/post.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("delete", recipeId);
                    jsonParam.put("password", username);
                    Log.i("JSON", jsonParam.toString());
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG", conn.getResponseMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
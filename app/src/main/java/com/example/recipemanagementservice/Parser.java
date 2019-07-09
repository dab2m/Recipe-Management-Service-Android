package com.example.recipemanagementservice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafatozluoglu on 07.07.2019
 */
public class Parser {

    private List<Food> foodList = new ArrayList<>();
    private String username;
    private String password;
    private static String login;

    public Parser(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
        this.login = "https://recipemanagementservice495.herokuapp.com/login.php?username=" + username + "&password=" + password;
    }

    public void readAllRecipes(String login) throws IOException {

        Document anasayfa = Jsoup.connect(login).get();
        Elements elements = anasayfa.select("div#tarifler");

        Elements tarifler = elements.select("div.row");

        Elements tarifIsimleri = tarifler.select("div.col-md-8 a[href]");
        Elements tarifAciklamalari = tarifler.select("div.col-md-8 p");
        Elements tarifEtiketleri = tarifler.select("div.col-md-4 li");
        Elements tarifResimleri = anasayfa.getElementsByTag("img");

        List<String> tarifIsimListesi = tarifIsimleri.eachText();
        List<String> tarifAciklamalariListesi = tarifAciklamalari.eachText();
        List<String> tarifEtiketleriListesi = tarifEtiketleri.eachText();

        /*for (Element el : tarifResimleri) {
            String src = el.absUrl("src");
            System.out.println(src);
        }*/

        for (int i = 0; i < tarifIsimListesi.size(); i++) {
            Food food = new Food();
            food.setFoodName(tarifIsimListesi.get(i));
            food.setFoodDescription(tarifAciklamalariListesi.get(i));
            food.setFoodTag(tarifEtiketleriListesi.get(i));
            food.setFoodTag(tarifEtiketleriListesi.get(i));
            foodList.add(food);
        }

        System.out.println(foodList);
    }

    public void readMyRecipes() throws IOException {

        String login = "https://recipemanagementservice495.herokuapp.com/tariflerim.php?username=a&password=a";

        Document anasayfa = Jsoup.connect(login).get();

        System.out.println(anasayfa);

        /*
        Elements elements = anasayfa.select("div#tarifler");

        Elements tarifler = elements.select("div.row");

        Elements tarifIsimleri = tarifler.select("div.col-md-8 a[href]");
        Elements tarifAciklamalari = tarifler.select("div.col-md-8 p");
        Elements tarifEtiketleri = tarifler.select("div.col-md-4 li");
        Elements tarifResimleri = anasayfa.getElementsByTag("img");

        List<String> tarifIsimListesi = tarifIsimleri.eachText();
        List<String> tarifAciklamalariListesi = tarifAciklamalari.eachText();
        List<String> tarifEtiketleriListesi = tarifEtiketleri.eachText();

        //for (Element el : tarifResimleri) {
        //    String src = el.absUrl("src");
        //    System.out.println(src);
        //}

        for (int i = 0; i < tarifIsimListesi.size(); i++) {
            Food food = new Food();
            food.setFoodName(tarifIsimListesi.get(i));
            food.setFoodDescription(tarifAciklamalariListesi.get(i));
            food.setFoodTag(tarifEtiketleriListesi.get(i));
            food.setFoodTag(tarifEtiketleriListesi.get(i));
            foodList.add(food);
        }
*/
        System.out.println(foodList);
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

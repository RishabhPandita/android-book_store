package com.pandita.rishabh.booksearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.author;

/**
 * Created by Rishabh on 19-05-2017.
 */

public class QueryUtils {

    private static Bitmap bmp =null;
    private QueryUtils() {
    }

    public static ArrayList<Book> fetchBookData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("QueryUtilsUtils.class", " Error closing input stream", e);
        }

        ArrayList<Book> bookList = extractBookInfo(jsonResponse);
        return bookList;
    }


    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("QueryUtils", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;

    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Error creating URL ", e);
        }
        return url;
    }


    public static ArrayList<Book> extractBookInfo(String jsonResponse) {
        ArrayList<Book> bookList = new ArrayList<Book>();


        try{
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray item = jsonObj.getJSONArray("items");

            for(int i =0;i< item.length();i++){

                JSONObject jsonCurrItem =item.getJSONObject(i);
                JSONObject volInfo = jsonCurrItem.getJSONObject("volumeInfo");
                JSONObject saleInfo = jsonCurrItem.getJSONObject("saleInfo");
                String saleability = saleInfo.getString("saleability");
                String bookTitle = volInfo.getString("title");
                JSONArray jsonAuthor = volInfo.getJSONArray("authors");

                if(volInfo.has("imageLinks")) {
                    JSONObject imageLinks = volInfo.getJSONObject("imageLinks");
                    String imageLinkString = imageLinks.getString("smallThumbnail");
                    URL url = new URL(imageLinkString);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }
                String buyLink="NA";
                String previewLink = volInfo.getString("previewLink");
                if(saleability.equals("FOR_SALE")){
                    buyLink=saleInfo.getString("buyLink");
                }


                String author="";
                for(int j=0;j<jsonAuthor.length();j++){
                    author=author+" "+jsonAuthor.getString(j);
                }

                bookList.add(new Book(bookTitle,author,bmp,previewLink,buyLink));
            }
        }
        catch(JSONException e){
            Log.e("QueryUtils", "Problem parsing the Book JSON results", e);} catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }
}

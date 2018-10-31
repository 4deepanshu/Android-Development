package com.schoolshieldchild_ui.model.uploadimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceConnection;
import com.schoolshieldchild_ui.view.services.UploadGalleryImages;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by root on 25/7/16.
 */
public class UploadGalleryImagesToServer extends AsyncTask<String, String, String> {
    public static File imagefile = null;
    String imagepath = "";

    public UploadGalleryImagesToServer(String imagepath) {
        this.imagepath = imagepath;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        MultipartEntity reqEntity = new MultipartEntity();
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(WebServiceConnection.baseUrl + "uploadGallery");
        try {
            reqEntity.addPart("student_id",
                    new StringBody(SharedPref.getString(MyApplication.STUDENT_ID)));
            imagefile = new File(imagepath);
            if (imagefile != null) {
                reqEntity.addPart("galleryimage", new FileBody(imagefile, "image/jpg"));
            }
            request.setEntity(reqEntity);
            HttpResponse httpResponse = client.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
            Log.v("response", "" + response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject resultResponse = jsonObject.getJSONObject("result");
            UploadGalleryImages.getinstance().updateDataBase(resultResponse);
        } catch (JSONException e) {
            e.printStackTrace();
            UploadGalleryImages.getinstance().updateDataBase(null);
        }

    }

    public byte[] setConvetStrintoByte(String path) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bmp = BitmapFactory.decodeFile(path, option);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data1 = bos.toByteArray();
        return data1;
    }
}

package com.example.lofm.githubfollowers.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LOFM on 23/02/2017.
 */
public class ImageDownloader extends AsyncTask<String, String, String> {
    URL ImageUrl;
    Bitmap bmImg = null;
    private Context context;
    private ProgressDialog pDialog;

    public ImageDownloader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... args) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            ImageUrl = new URL(args[0]);
            HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Config.ARGB_8888;
            bmImg = BitmapFactory.decodeStream(is, null, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = ImageUrl.getPath();
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            File filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File dir = new File(filepath, "GHAvatars");
            if(!dir.exists()){
                dir.mkdir();
            }
            String fileName = idStr + ".jpeg";
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            bmImg.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            File imageFile = file;
            MediaScannerConnection.scanFile(context, new String[]{imageFile.getPath()}, new String[]{"image/jpeg"}, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String args) {
        if (bmImg == null) {
            Toast.makeText(context, "Image still loading...", Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
        } else {
            if (pDialog != null) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }
            Toast.makeText(context, "Avatar Successfully Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
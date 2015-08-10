package com.teachertipsforparents.bullybx.customclasses;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by christinajackey on 7/30/15.
 */
public class PostExample {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String string) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN , string);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
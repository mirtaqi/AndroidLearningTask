package ssm.learning.androidLearningTask.FinalTask.WebService;

import android.net.Uri;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import ssm.learning.androidLearningTask.session6.Session6TaskActivity;
import ssm.learning.androidLearningTask.session6.Vehicles;

public class OmdbApiInterface {
    private static final String BASE_URL  = "www.omdbapi.com";
    public static void getMovie(String imdbId,final SearchCallback<MovieInfo> callback)
    {
        if(imdbId!=null && !imdbId.isEmpty())
        {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(BASE_URL)
                    .appendQueryParameter("apikey","82a1afc1")
                    .appendQueryParameter("i", imdbId)
                    .appendQueryParameter("plot","full");


            String url = builder.build().toString();
            final AsyncHttpClient client=new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Gson gson = new Gson();
                    String str = new String(responseBody, StandardCharsets.US_ASCII);
                    MovieInfo searchResult = gson.fromJson(str, MovieInfo.class);
                    if (searchResult == null) {
                        ErrorResponse err = gson.fromJson(str, ErrorResponse.class);
                        if (err != null) {
                            if (callback != null)
                                callback.OnFailure(err.getError());
                        } else {
                            if (callback != null)
                                callback.OnFailure("Invalid response");
                        }

                    } else {
                        if (searchResult.getError() != null && !searchResult.getError().isEmpty()) {
                            if (callback != null)
                                callback.OnFailure(searchResult.getError());
                        } else {
                            if (callback != null)
                                callback.OnSuccess(searchResult);
                        }
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (callback!=null)
                        callback.OnFailure("Error getting result from web service!");
                }
            });
            if(callback!=null)
                callback.OnStart();
        }
    }
    public static void Search(String title, String type, int page, final SearchCallback<OmdbSearchResult> callback)
    {

        if(title!=null && !title.isEmpty())
        {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(BASE_URL)
                    .appendQueryParameter("apikey","82a1afc1")
                    .appendQueryParameter("s", title);

            if(type!=null && !type.isEmpty())
            {
                builder.appendQueryParameter("type",type);
            }
            if(page>0)
            {
                builder.appendQueryParameter("page",Integer.toString(page));
            }
            String url = builder.build().toString();
            final AsyncHttpClient client=new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Gson gson=new Gson();
                    String str=new String(responseBody, StandardCharsets.US_ASCII);
                    OmdbSearchResult searchResult= gson.fromJson(str,OmdbSearchResult.class);
                    if (searchResult==null)
                    {
                        ErrorResponse err= gson.fromJson(str,ErrorResponse.class);
                        if(err!=null)
                        {
                            if (callback!=null)
                                callback.OnFailure(err.getError());
                        }
                        else {
                            if (callback!=null)
                                callback.OnFailure("Invalid response");
                        }

                    }
                    else
                    {
                        if(searchResult.getError()!=null && !searchResult.getError().isEmpty())
                        {
                            if (callback!=null)
                                callback.OnFailure(searchResult.getError());
                        }
                        else {
                            if (callback != null)
                                callback.OnSuccess(searchResult);
                        }
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (callback!=null)
                        callback.OnFailure("Error getting result from web service!");
                }
            });
            if(callback!=null)
                callback.OnStart();
        }

    }
    public interface SearchCallback<TResult>
    {
        public void OnStart();
        public void OnSuccess(TResult result);
        public void OnFailure(String reason);
    }
}

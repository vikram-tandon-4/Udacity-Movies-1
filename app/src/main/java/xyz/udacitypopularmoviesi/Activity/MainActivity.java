package xyz.udacitypopularmoviesi.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.udacitypopularmoviesi.Adapters.MovieGridAdapter;
import xyz.udacitypopularmoviesi.ApiClient;
import xyz.udacitypopularmoviesi.Interfaces.ApiInterface;
import xyz.udacitypopularmoviesi.Models.MovieListResponseBean;
import xyz.udacitypopularmoviesi.Models.Results;
import xyz.udacitypopularmoviesi.R;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    GridView gridview;
    Results movieData;
    String MOVIEOBJECT = "movieObject";
    private final static String API_KEY = "";
    protected Toolbar toolbar;
    private TextView toolBarHeaderText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        gridview = (GridView) findViewById(R.id.gridview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarHeaderText = (TextView) findViewById(R.id.tvTbTitle);
        setUpToolbar();

        if (isNetWorkAvailable(mContext))
            getTopRatedMovies();
        else
            openDialog();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                movieData = (Results) adapterView.getItemAtPosition(i);
// sending movie data to details screen
                Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                intent.putExtra(MOVIEOBJECT, movieData);

                startActivity(intent);
            }
        });

    }

    private void getTopRatedMovies() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        progressDialog = ProgressDialog.show(this, "","Please Wait...", true);
        Call<MovieListResponseBean> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieListResponseBean>() {
            @Override
            public void onResponse(Call<MovieListResponseBean> call, Response<MovieListResponseBean> response) {
                List<Results> movies = response.body().getResults();
                Log.e("API RESPONSE", "Number of movies received: " + movies.size());

                progressDialog.dismiss();
                // resetting adapter with fresh data
                gridview.setAdapter(new MovieGridAdapter(mContext, movies));
            }

            @Override
            public void onFailure(Call<MovieListResponseBean> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("API RESPONSE", t.toString());
            }
        });
    }


    private void getPopularMovies() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        progressDialog = ProgressDialog.show(this, "","Please Wait...", true);

        Call<MovieListResponseBean> call = apiService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieListResponseBean>() {
            @Override
            public void onResponse(Call<MovieListResponseBean> call, Response<MovieListResponseBean> response) {
                List<Results> movies = response.body().getResults();
                Log.e("API RESPONSE", "Number of movies received: " + movies.size());
                progressDialog.dismiss();
                // resetting adapter with fresh data
                gridview.setAdapter(new MovieGridAdapter(mContext, movies));
            }

            @Override
            public void onFailure(Call<MovieListResponseBean> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("API RESPONSE", t.toString());
            }
        });
    }

    private void setUpToolbar() {
        if (toolbar != null) {
            setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolBarHeaderText.setText(getString(R.string.toolbar_screen_one));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (isNetWorkAvailable(mContext)) {
            if (id == R.id.top_rated) {
                // Api call
                getTopRatedMovies();
            } else {
                // Api call
                getPopularMovies();
            }
        }
            else
                // No internet connection Alert
                openDialog();


        return true;
        //   return super.onOptionsItemSelected(item);
    }

//checking internet connection
    public static boolean isNetWorkAvailable(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connMgr == null) {
                return false;
            } else if (connMgr.getActiveNetworkInfo() != null && connMgr.getActiveNetworkInfo().isAvailable() && connMgr.getActiveNetworkInfo().isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public void openDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.dialog_text);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}



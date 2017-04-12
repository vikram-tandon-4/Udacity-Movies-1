package xyz.udacitypopularmoviesi.Activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.udacitypopularmoviesi.Models.Results;
import xyz.udacitypopularmoviesi.R;

/**
 * Created by vikra on 8/27/2016.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    private TextView toolBarHeaderText;
    private Results movieData;
    private String MOVIEOBJECT="movieObject";
    private TextView overview,rating,duration,year,movie_name;
    Context mContext;
    String BaseUrl="http://image.tmdb.org/t/p/";
    String Imagequality="w185/";
    private ImageView movie_poster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        mContext=this;

        movie_poster=(ImageView)findViewById(R.id.movie_poster);
        movieData= (Results) getIntent().getSerializableExtra(MOVIEOBJECT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarHeaderText = (TextView) findViewById(R.id.tvTbTitle);
        overview = (TextView) findViewById(R.id.overview);
        rating = (TextView) findViewById(R.id.rating);
        year = (TextView) findViewById(R.id.year);
        movie_name = (TextView) findViewById(R.id.movie_name);

        //setting up toolbar
        setUpToolbar();

        // setting data from object

        overview.setText(movieData.getOverview());
        rating.setText(Double.toString(movieData.getVote_average()).substring(0,3)+"/10");
        year.setText(movieData.getRelease_date().substring(0,4));
        movie_name.setText(movieData.getTitle());

        // loading image using provided url
        Picasso.with(mContext)
                .load(BaseUrl +Imagequality+movieData.getPoster_path())
                .placeholder(R.drawable.ic_movie_white_48dp)
                .error(R.drawable.ic_movie_white_48dp)
                .into(movie_poster);


    }

    private void setUpToolbar()
    {
        if (toolbar != null) {
            setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolBarHeaderText.setText(getString(R.string.movie_detail));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_details, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {

        }
        return true;
    }



}

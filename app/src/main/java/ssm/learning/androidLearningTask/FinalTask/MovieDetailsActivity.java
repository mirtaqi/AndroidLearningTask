package ssm.learning.androidLearningTask.FinalTask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ssm.learning.androidLearningTask.FinalTask.Database.OmdbDatabaseOpenHelper;
import ssm.learning.androidLearningTask.FinalTask.WebService.MovieInfo;
import ssm.learning.androidLearningTask.FinalTask.WebService.OmdbApiInterface;
import ssm.learning.androidLearningTask.FinalTask.WebService.Rating;
import ssm.learning.androidLearningTask.R;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView _titleTV,_yearTV,_ratedTV,_relasedTV,_runtimeTV,
    _genreTV,_directorTV,_writerTV,_actorsTV,_plotTV,_languageTV,_countryTV,
    _awardsTV,_ratingsTV,_metaScoreTV,_imdbRatingTV,_imdbVotesTV,_imdbIdTV,_typeTV,
    _dvdTV,_boxOfficeTV,_productionTV,_websiteTV;
    private ImageView _posterImageView;
    private ProgressBar _loadingProgressBar;
    private String _imdbId;
    private OmdbDatabaseOpenHelper _database;
    private MovieInfo _movieInfo;
    private FloatingActionButton _saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Movie details");
        _titleTV=findViewById(R.id.title_text_view);
        _yearTV=findViewById(R.id.year_text_view);
        _ratedTV=findViewById(R.id.rated_text_view);
        _relasedTV=findViewById(R.id.released_text_view);
        _runtimeTV=findViewById(R.id.runtime_text_view);

        _genreTV=findViewById(R.id.genre_text_view);
        _directorTV=findViewById(R.id.director_text_view);
        _writerTV=findViewById(R.id.writer_text_view);
        _actorsTV=findViewById(R.id.actors_text_view);
        _plotTV=findViewById(R.id.plot_text_view);
        _languageTV=findViewById(R.id.language_text_view);
        _countryTV=findViewById(R.id.country_text_view);

        _awardsTV=findViewById(R.id.awards_text_view);
        _ratingsTV=findViewById(R.id.rating_text_view);
        _metaScoreTV=findViewById(R.id.meta_score_text_view);
        _imdbRatingTV=findViewById(R.id.imdb_rating_score_text_view);
        _imdbVotesTV=findViewById(R.id.imdb_votes_text_view);
        _imdbIdTV=findViewById(R.id.imdb_id_text_view);
        _typeTV=findViewById(R.id.type_text_view);

        _dvdTV=findViewById(R.id.dvd_text_view);
        _boxOfficeTV=findViewById(R.id.box_office_text_view);
        _productionTV=findViewById(R.id.production_text_view);
        _websiteTV=findViewById(R.id.website_text_view);

        _saveButton=findViewById(R.id.save_movie_button);
        _saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_movieInfo!=null)
                {
                    _database.insertMovie(_movieInfo);
                    _saveButton.setVisibility(View.GONE);
                    Toast.makeText(MovieDetailsActivity.this,"Saved to database",Toast.LENGTH_SHORT).show();

                }
            }
        });
        _posterImageView=findViewById(R.id.poster_image_view);
        _loadingProgressBar=findViewById(R.id.loading_detail_progressbar);

        _database=new OmdbDatabaseOpenHelper(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //super.onBackPressed();
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        _imdbId=getIntent().getStringExtra("imdb_id");
        _loadingProgressBar.setVisibility(View.VISIBLE);
        _movieInfo= _database.getMovie(_imdbId);

        _loadingProgressBar.setVisibility(View.GONE);
        if(_movieInfo==null)
        {
            _saveButton.setVisibility(View.VISIBLE);
            OmdbApiInterface.getMovie(_imdbId, new OmdbApiInterface.SearchCallback<MovieInfo>() {
                @Override
                public void OnStart() {
                    _loadingProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void OnSuccess(MovieInfo movieInfo) {

                    _movieInfo=movieInfo;
                    _loadingProgressBar.setVisibility(View.GONE);
                    display();
                }

                @Override
                public void OnFailure(String reason) {
                    _loadingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(MovieDetailsActivity.this,"Error getting movie detail",Toast.LENGTH_LONG).show();
                    finish();
                }
            });

        }
        else {
            _saveButton.setVisibility(View.GONE);
            display();
        }



    }
    private void display()
    {

        MovieInfo m=_movieInfo;
        if(m!=null)
        {
            _titleTV.setText(m.getTitle());
            _yearTV.setText(m.getYear());
            _ratedTV.setText(m.getRated());
            _relasedTV.setText(m.getReleased());
            _runtimeTV.setText(m.getRuntime());
            _genreTV.setText(m.getGenre());
            _directorTV.setText(m.getDirector());
            _writerTV.setText(m.getWriter());
            _actorsTV.setText(m.getActors());
            _plotTV.setText(m.getPlot());
            _languageTV.setText(m.getLanguage());
            _countryTV.setText(m.getCountry());
            _awardsTV.setText(m.getAwards());
            Glide.with(this).load(m.getPoster()).placeholder(R.drawable.ic_image).into(_posterImageView);
            String ratingTxt="";
            for(Rating r:m.getRatings())
            {
                ratingTxt+="\u25BA"+r.getSource() +" = " +r.getValue()+"\n";
            }
            _ratingsTV.setText(ratingTxt);
            _metaScoreTV.setText(m.getMetascore());
            _imdbRatingTV.setText(m.getImdbRating());
            _imdbVotesTV.setText(m.getImdbVotes());
            _imdbIdTV.setText(m.getImdbID());
            _typeTV.setText(m.getType());
            _dvdTV.setText(m.getDVD());
            _boxOfficeTV.setText(m.getBoxOffice());
            _productionTV.setText(m.getProduction());
            _websiteTV.setText(m.getWebsite());

        }
    }



}
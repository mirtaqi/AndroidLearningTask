package ssm.learning.androidLearningTask.FinalTask;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ssm.learning.androidLearningTask.FinalTask.Database.OmdbDatabaseOpenHelper;
import ssm.learning.androidLearningTask.FinalTask.WebService.MovieInfo;
import ssm.learning.androidLearningTask.FinalTask.WebService.OmdbApiInterface;
import ssm.learning.androidLearningTask.R;

public class OmdbItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView _posterImageView;
    private TextView _titleTextView;
    private TextView _yearTextView;
    private TextView _idTextView;
    private TextView _typeTextView;
    private FloatingActionButton _saveToDatabaseButton;
    private boolean _offline;
    private OmdbDatabaseOpenHelper _database;
    private MovieInfo _movieInfo;
    private FloatingActionButton _deleteFromDatabaseButton,_showDetailButton;
    private ProgressBar _savingProgressBar;
    private CardView _cardView;
    OnlineSearchResultAdapter _adapter;
    public OmdbItemViewHolder(@NonNull final View itemView, boolean offline, final OmdbDatabaseOpenHelper database,OnlineSearchResultAdapter adapter) {
        super(itemView);
        _adapter=adapter;
        _offline=offline;
        _database=database;

        initViews();
    }


    private void initViews()
    {
        _cardView=itemView.findViewById(R.id.root_card_view);
        _cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _adapter.viewDetails(getAdapterPosition());
            }
        });
        _saveToDatabaseButton=itemView.findViewById(R.id.save_to_database_button);
        _deleteFromDatabaseButton=itemView.findViewById(R.id.delete_from_database_button);
        _savingProgressBar=itemView.findViewById(R.id.saving_progress_bar);
        _showDetailButton=itemView.findViewById(R.id.show_movie_detail_button);

        _posterImageView=itemView.findViewById(R.id.poster_image);
        _idTextView=itemView.findViewById(R.id.id_text);
        _titleTextView=itemView.findViewById(R.id.title_text);
        _yearTextView=itemView.findViewById(R.id.year);
        _typeTextView=itemView.findViewById(R.id.type_text);

        _showDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _adapter.viewDetails(getAdapterPosition());
            }
        });
        _deleteFromDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(_movieInfo !=null) {
                    _adapter.deleteItem(getAdapterPosition());


                }
            }
        });
        _saveToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_movieInfo !=null) {
                    //_savingProgressBar=itemView.findViewById(R.id.saving_progress_bar);
                    OmdbApiInterface.getMovie(_movieInfo.getImdbID(), new OmdbApiInterface.SearchCallback<MovieInfo>() {
                        @Override
                        public void OnStart() {
                            _saveToDatabaseButton.setEnabled(false);
                            _savingProgressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void OnSuccess(MovieInfo movieInfo) {
                            _savingProgressBar.setVisibility(View.GONE);
                            _database.insertMovie(movieInfo);
                            Toast.makeText(itemView.getContext(),"Item saved to database",Toast.LENGTH_SHORT).show();
                            _saveToDatabaseButton.setEnabled(false);
                        }

                        @Override
                        public void OnFailure(String reason) {
                            Toast.makeText(itemView.getContext(),"Error in getting movie's detail",Toast.LENGTH_SHORT).show();
                            _savingProgressBar.setVisibility(View.GONE);
                            _saveToDatabaseButton.setEnabled(true);
                        }
                    });

                }
            }
        });
        if(_offline) {
            _saveToDatabaseButton.setVisibility(View.GONE);
            _deleteFromDatabaseButton.setVisibility(View.VISIBLE);
        }
        else
        {
            _saveToDatabaseButton.setVisibility(View.VISIBLE);
            _deleteFromDatabaseButton.setVisibility(View.GONE);
        }



    }
    public void bind(MovieInfo movieInfo) {
       // initViews();
        _movieInfo = movieInfo;
        if(_movieInfo !=null)
        {

            Glide.with(this.itemView).load(_movieInfo.getPoster()).placeholder(R.drawable.ic_image).into(_posterImageView);
            _idTextView.setText(_movieInfo.getImdbID());
            _titleTextView.setText(_movieInfo.getTitle());
            _yearTextView.setText(_movieInfo.getYear());
            _typeTextView.setText(_movieInfo.getType());
            if(!_offline)
            {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if(_database.existsOmdbRecord(_movieInfo.getImdbID()))
                        {
                            _saveToDatabaseButton.setEnabled(false);
                        }
                        else
                            _saveToDatabaseButton.setEnabled(true);
                    }
                });

            }
        }
    }
}

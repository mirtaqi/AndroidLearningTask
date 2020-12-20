package ssm.learning.androidLearningTask.FinalTask.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Vector;

import ssm.learning.androidLearningTask.FinalTask.Database.OmdbDatabaseOpenHelper;
import ssm.learning.androidLearningTask.FinalTask.FinalTaskMainActivity;
import ssm.learning.androidLearningTask.FinalTask.MovieDetailsActivity;
import ssm.learning.androidLearningTask.FinalTask.OnlineSearchResultAdapter;
import ssm.learning.androidLearningTask.FinalTask.WebService.OmdbApiInterface;
import ssm.learning.androidLearningTask.FinalTask.WebService.OmdbSearchResult;
import ssm.learning.androidLearningTask.FinalTask.WebService.MovieInfo;
import ssm.learning.androidLearningTask.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OmdbSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OmdbSearchFragment extends Fragment {

    private TextView _searchTextView;
    private MaterialButton _searchButton;
    private RecyclerView _onlineSearchResultRecyclerView;
    private OnlineSearchResultAdapter _adapter;
    private Vector<MovieInfo> _movieInfoList;
    private OmdbSearchResult _searchResult;
    private ProgressBar _loadingProgress;
    private int _currentPage=1;
    private OmdbDatabaseOpenHelper _database;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean _offline;
    public OmdbSearchFragment(boolean offline) {
        super();
        _offline=offline;

        // Required empty public constructor
    }
    public OmdbSearchFragment() {
        super();
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnlineOmdbSearchFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static OmdbSearchFragment newInstance(String param1, String param2) {
        OmdbSearchFragment fragment = new OmdbSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_omdb_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        _database=new OmdbDatabaseOpenHelper(this.getContext());
        _onlineSearchResultRecyclerView=view.findViewById(R.id.online_search_result_recycler_view);
        _movieInfoList =new Vector<>();
        _adapter=new OnlineSearchResultAdapter(_movieInfoList, _offline, _database, new OnlineSearchResultAdapter.OnDeleteItemRequest() {
            @Override
            public void OnDeleteItem(final int index) {
                AlertDialog alertDialog=new AlertDialog.Builder(OmdbSearchFragment.this.getContext()).create();
                alertDialog.setTitle("Delete movie from database");
                alertDialog.setTitle("Are you sure?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        MovieInfo m= _movieInfoList.get(index);
                        _database.delete(m.getImdbID());
                        _movieInfoList.remove(index);
                        _adapter.notifyDataSetChanged();
                        Toast.makeText(OmdbSearchFragment.this.getContext(),"Item deleted from database",Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        }, new OnlineSearchResultAdapter.OnViewDetailsRequest() {
            @Override
            public void OnViewDetails(int index) {
                Intent intent=new Intent(OmdbSearchFragment.this.getContext(), MovieDetailsActivity.class);
                intent.putExtra("imdb_id",_movieInfoList.get(index).getImdbID());
                startActivity(intent);
            }
        });
        _onlineSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        _onlineSearchResultRecyclerView.setAdapter(_adapter);

        CardView cv=view.findViewById(R.id.card_view);
        if(_offline)
        {
            cv.setVisibility(View.GONE);
        }
        else
        {
            cv.setVisibility(View.VISIBLE);
        }



        if(!_offline) {
            _onlineSearchResultRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        _currentPage += 1;

                        Search();
                    }
                }
            });
        }

        _searchTextView=view.findViewById(R.id.title_edit_text);
        _searchButton=view.findViewById(R.id.online_search_button);
        _loadingProgress=view.findViewById(R.id.loading_progress);
        _loadingProgress.setVisibility(View.GONE);
        _searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _currentPage=1;

                _movieInfoList.clear();
                  Search();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if(_offline) {
            ((FinalTaskMainActivity)getActivity()).getSupportActionBar().setTitle("Saved Movies");
            Search();
        }
        else
        {
            ((FinalTaskMainActivity)getActivity()).getSupportActionBar().setTitle("Search the Open Movie Database");
        }
    }

    private boolean _isBusy=false;
    private void Search()
    {
        if(_offline)
        {
            _loadingProgress.setVisibility(View.VISIBLE);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    _movieInfoList.clear();
                    OmdbDatabaseOpenHelper db=new OmdbDatabaseOpenHelper(OmdbSearchFragment.this.getContext());
                    _movieInfoList.addAll(db.getAllSearch());
                    _adapter.notifyDataSetChanged();
                }
            });

             _loadingProgress.setVisibility(View.GONE);
        }
        else {

            if(_isBusy)
                return;
            OmdbApiInterface.Search(_searchTextView.getText().toString(), null, _currentPage, new OmdbApiInterface.SearchCallback<OmdbSearchResult>() {
                @Override
                public void OnStart() {
                    _isBusy=true;
                    _loadingProgress.setVisibility(View.VISIBLE);
                    _onlineSearchResultRecyclerView.setEnabled(false);
                    _searchButton.setEnabled(false);
                }

                @Override
                public void OnSuccess(OmdbSearchResult result) {
                    _loadingProgress.setVisibility(View.GONE);
                    _onlineSearchResultRecyclerView.setEnabled(true);
                    _searchButton.setEnabled(true);
                    _searchResult = result;
                    _movieInfoList.addAll(result.getMovieInfos());
                    _adapter.notifyDataSetChanged();
                    _isBusy=false;
                }

                @Override
                public void OnFailure(String reason) {
                    _loadingProgress.setVisibility(View.GONE);
                    _onlineSearchResultRecyclerView.setEnabled(true);
                    _searchButton.setEnabled(true);
                    Toast.makeText(OmdbSearchFragment.this.getContext(), reason, Toast.LENGTH_SHORT).show();
                    _isBusy=false;
                }
            });
        }
    }
}
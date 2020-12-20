package ssm.learning.androidLearningTask.FinalTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ssm.learning.androidLearningTask.FinalTask.Database.OmdbDatabaseOpenHelper;
import ssm.learning.androidLearningTask.FinalTask.WebService.MovieInfo;
import ssm.learning.androidLearningTask.R;

public class OnlineSearchResultAdapter extends RecyclerView.Adapter<OmdbItemViewHolder> {

    private List<MovieInfo> _movieInfoList;
    private boolean _offline=false;
    private OmdbDatabaseOpenHelper _database;
    private OnDeleteItemRequest _onDeleteItemRequest;
    private OnViewDetailsRequest _onViewDetailsRequest;
    public OnlineSearchResultAdapter(List<MovieInfo> movieInfoList, boolean offline, OmdbDatabaseOpenHelper database, OnDeleteItemRequest onDeleteItemRequest,OnViewDetailsRequest onViewDetailsRequest11)
    {
        _onDeleteItemRequest=onDeleteItemRequest;
        _onViewDetailsRequest=onViewDetailsRequest11;
        _database=database;
        _movieInfoList = movieInfoList;
        _offline=offline;
    }

    @NonNull
    @Override
    public OmdbItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.omdb_search_result_item,parent,false);
        return new OmdbItemViewHolder(view,_offline,_database,this);
    }

    @Override
    public void onBindViewHolder(@NonNull OmdbItemViewHolder holder, int position) {
        holder.bind(_movieInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return _movieInfoList.size();
    }

    public void deleteItem(int adapterPosition) {
        if(_onDeleteItemRequest!=null)
        {
            _onDeleteItemRequest.OnDeleteItem(adapterPosition);
        }
    }
    public void viewDetails(int index)
    {
        if(_onViewDetailsRequest!=null)
        {
            _onViewDetailsRequest.OnViewDetails(index);
        }
    }
    public interface OnDeleteItemRequest{
        public void OnDeleteItem(int index);
    }
    public interface OnViewDetailsRequest{
        public void OnViewDetails(int index);
    }
}

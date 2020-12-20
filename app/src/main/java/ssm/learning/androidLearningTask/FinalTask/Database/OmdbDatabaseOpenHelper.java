package ssm.learning.androidLearningTask.FinalTask.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.Vector;

import ssm.learning.androidLearningTask.FinalTask.WebService.MovieInfo;

public class OmdbDatabaseOpenHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="OMDBO";
    private static final int DATABASE_VERSION=1;
    private static final String OMDB_TABLE_NAME="omdb";
    private static final String ID_COLUMN="id";
    private static final String JSON_DATA_COLUMN="json";
    private static  final String TITLE_COLUMN="title";
    private static final  String YEAR_COLUMN="year";
    private static final String TYPE_COLUMN="type";
    private static final String POSTER_COLUMN="poster";

    public OmdbDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        String CREATE_TABLE="CREATE TABLE " + OMDB_TABLE_NAME+
                "(" +
                ID_COLUMN +" TEXT  PRIMARY KEY,"+
                TITLE_COLUMN +" TEXT NOT NULL,"+
                YEAR_COLUMN +" TEXT NOT NULL,"+
                TYPE_COLUMN +" TEXT NOT NULL,"+
                POSTER_COLUMN +" TEXT"+")";
        db.execSQL(CREATE_TABLE);

         */
        String CREATE_TABLE="CREATE TABLE " + OMDB_TABLE_NAME+
                "(" +
                ID_COLUMN +" TEXT  PRIMARY KEY,"+
                JSON_DATA_COLUMN +" TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean existsOmdbRecord(String id)
    {
        String query="SELECT COUNT("+ID_COLUMN+") " +
                "FROM "+OMDB_TABLE_NAME +
                " WHERE "+ID_COLUMN+" = '"+id+"';";
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery(query,null);

        if(cursor.moveToNext())
        {
            int count= cursor.getInt(0);
            db.close();
            return count>0;
        }
        db.close();
        return false;
    }
    public void insertMovie(MovieInfo movieInfo)
    {
        //String insertCmd="INSERT INTO "+OMDB_TABLE_NAME + "("+ID_COLUMN+" , "+TITLE_COLUMN+" , "+YEAR_COLUMN+","+TYPE_COLUMN+","+POSTER_COLUMN+")"+
           //     " VALUES ("+"'"+ movieInfo.getImdbID()+"' , '"+ movieInfo.getTitle().replace("'","''")+"' , '"+ movieInfo.getYear()+"' , '"+ movieInfo.getType()+"' , '"+ movieInfo.getPoster().replace("'","''")+"')";


        Gson gson=new Gson();
        String json= gson.toJson(movieInfo).toString();
        //String insertCmd="INSERT INTO "+OMDB_TABLE_NAME + "("+ID_COLUMN+" , "+JSON_DATA_COLUMN+")"+
         //       " VALUES ("+"'"+ movieInfo.getImdbID()+"' , '"+ movieInfo.getTitle().replace("'","''")+"' , '"+ movieInfo.getYear()+"' , '"+ movieInfo.getType()+"' , '"+ movieInfo.getPoster().replace("'","''")+"')";

        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ID_COLUMN,movieInfo.getImdbID());
        values.put(JSON_DATA_COLUMN,json);

        db.insert(OMDB_TABLE_NAME,null,values);
       // db.execSQL(insertCmd);
        db.close();
    }
    public Vector<MovieInfo> getAllSearch()
    {
      //  String query="SELECT "+ID_COLUMN+" , "+TITLE_COLUMN+" , "+YEAR_COLUMN+","+TYPE_COLUMN+","+POSTER_COLUMN+
      //          " FROM "+ OMDB_TABLE_NAME ;
        String query="SELECT "+ID_COLUMN+" , "+JSON_DATA_COLUMN+" FROM "+OMDB_TABLE_NAME;
        SQLiteDatabase db=getReadableDatabase();
        Vector<MovieInfo> result=new Vector<>();
        Cursor cursor= db.rawQuery(query,null);

        Gson gson=new Gson();
        while(cursor.moveToNext())
        {
           //MovieInfo s=new MovieInfo();
           /*
           s.setImdbID(cursor.getString(0));
           s.setTitle(cursor.getString(1));
           s.setYear(cursor.getString(2));
           s.setType(cursor.getString(3));
           s.setPoster(cursor.getString(4));*/
           String json=cursor.getString(1);
          MovieInfo movieInfo=  gson.fromJson(json,MovieInfo.class);
           result.add(movieInfo);
        }
        db.close();
        return result;
    }

    public MovieInfo getMovie(String imdbId)
    {
        String query="SELECT "+ID_COLUMN+" , "+JSON_DATA_COLUMN+" FROM "+OMDB_TABLE_NAME+" WHERE "+ID_COLUMN+"='"+imdbId+"'";
        SQLiteDatabase db=getReadableDatabase();

        Cursor cursor= db.rawQuery(query,null);
        MovieInfo result=null;
        Gson gson=new Gson();
        if(cursor.moveToNext())
        {
            String json=cursor.getString(1);
            result=  gson.fromJson(json,MovieInfo.class);

        }
        db.close();
        return result;
    }
    public void delete(String imdbID) {
        String cmd="DELETE FROM "+OMDB_TABLE_NAME+" WHERE "+ID_COLUMN+"='"+imdbID+"';";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(cmd);
        db.close();
    }
}

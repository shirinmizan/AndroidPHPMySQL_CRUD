package sheridan.ca.assignment1_shirinmizan_androidphp;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowFavorites extends ListActivity {
    final static String TABLE_NAME = "favorites";
    final static String FAVORITE_MOVIE = "moviename";
    final static String _ID = "_id";
    final static String[] columns = { _ID, FAVORITE_MOVIE };
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_favorites);

        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase(); //to write in the database

        Cursor c = readArtists();
        setListAdapter(new SimpleCursorAdapter(this, R.layout.list_layout, c,
                columns, new int[] { R.id._id, R.id.name }));


    }

    private Cursor readArtists() {
        return db.query(TABLE_NAME, columns, null, new String[] {}, null, null,
                null);
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        Cursor cursor = (Cursor) l.getItemAtPosition(position);
        //final EditText ed = new EditText(this);
        final String moviename = cursor.getString(1);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Would you want to delete this?");
        //alertBuilder.setView(ed);

        alertBuilder.setNegativeButton("DELETE",
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(TABLE_NAME, FAVORITE_MOVIE + "=?",
                                new String[]{moviename});
                        Cursor c = readArtists();
                        setListAdapter(new SimpleCursorAdapter(getApplicationContext(),
                                R.layout.list_layout, c, columns, new int[]
                                {R.id._id, R.id.name}));

                    }

                });
        alertBuilder.setNeutralButton("CANCEL",
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

}

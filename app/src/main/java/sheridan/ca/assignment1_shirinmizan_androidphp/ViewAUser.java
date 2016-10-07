package sheridan.ca.assignment1_shirinmizan_androidphp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewAUser extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextFood;
    private EditText editTextTV;
    private EditText editTextMovie;

    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonSave;
    private Button buttonBack;

    private String id;

    //to save into SQLLite databse
    final static String TABLE_NAME = "favorites";
    final static String FAVORITE_MOVIE = "moviename";
    final static String _ID = "_id";
    final static String[] columns = { _ID, FAVORITE_MOVIE };
    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.FAV_ID);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextFood = (EditText) findViewById(R.id.editTextFavfood);
        editTextTV = (EditText) findViewById(R.id.editTextFavtv);
        editTextMovie = (EditText) findViewById(R.id.editTextFavMovie);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonSave = (Button) findViewById(R.id.buttonSaveFav);
        buttonBack = (Button) findViewById(R.id.buttonBackToUser);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        editTextId.setText(id);

        getFavorite();

        //SQlLite
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();


    }
    //SQLLite
    private void addToFavorite(){

        ContentValues values = new ContentValues();

        //get the names from user input
        String favmovie = editTextMovie.getText().toString();
        //System.out.println(artist_name);

        //save it to the database
        values.put(FAVORITE_MOVIE, favmovie);
        db.insert(TABLE_NAME, null, values);

        Toast.makeText(getBaseContext(),"Movie added to the favorite", Toast.LENGTH_LONG).show();

        //and show the name in the listview
        Intent intent = new Intent(this, ShowFavorites.class);
        startActivity(intent);

    }

    private void getFavorite(){

        class GetFavorite extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAUser.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showFavorites(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_ONE,id);
                return s;
            }
        }
        GetFavorite ge = new GetFavorite();
        ge.execute();
    }

    private void showFavorites(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Config.TAG_NAME);
            String email = c.getString(Config.TAG_EMAIL);
            String food = c.getString(Config.TAG_FAVFOOD);
            String tv = c.getString(Config.TAG_FAVTV);
            String movie = c.getString(Config.TAG_FAVMOVIE);

            editTextName.setText(name);
            editTextEmail.setText(email);
            editTextFood.setText(food);
            editTextTV.setText(tv);
            editTextMovie.setText(movie);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateFavorites(){
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String food  = editTextFood.getText().toString();
        final String tv = editTextTV.getText().toString();
        final String movie = editTextMovie.getText().toString();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAUser.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewAUser.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_FAV_ID,id);
                hashMap.put(Config.KEY_FAV_NAME,name);
                hashMap.put(Config.KEY_FAV_EMAIL,email);
                hashMap.put(Config.KEY_FAV_FAVFOOD,food);
                hashMap.put(Config.KEY_FAV_TVSHOW,tv);
                hashMap.put(Config.KEY_FAV_MOVIE,movie);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_FAV,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private void deleteFavorite(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAUser.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewAUser.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_FAV, id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteFavorite(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this employee?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteFavorite();
                        startActivity(new Intent(ViewAUser.this,ViewAllFavUser.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateFavorites();
        }

        if(v == buttonDelete){
            confirmDeleteFavorite();
        }

        if(v == buttonSave){
            addToFavorite();
        }
        if(v == buttonBack){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}





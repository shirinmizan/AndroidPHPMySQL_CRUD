package sheridan.ca.assignment1_shirinmizan_androidphp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Defining views
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextFood;
    private EditText editTextTv;
    private EditText editTextmovie;

    private Button buttonAdd;
    private Button buttonView;
    private Button buttonViewFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextFood = (EditText) findViewById(R.id.editTextFood);
        editTextTv = (EditText) findViewById(R.id.editTextTV);
        editTextmovie = (EditText) findViewById(R.id.editTextMovie);

        //editTextDesg = (EditText) findViewById(R.id.editTextDesg);
        //editTextSal = (EditText) findViewById(R.id.editTextSalary);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonViewFav = (Button) findViewById(R.id.buttonViewFav);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        buttonViewFav.setOnClickListener(this);
    }


    //Adding an employee
    private void addFavorite(){

        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String favfood = editTextFood.getText().toString().trim();
        final String favtv = editTextTv.getText().toString().trim();
        final String favmovie = editTextmovie.getText().toString().trim();

        class AddFavorite extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_FAV_NAME,name);
                params.put(Config.KEY_FAV_EMAIL,email);
                params.put(Config.KEY_FAV_FAVFOOD,favfood);
                params.put(Config.KEY_FAV_TVSHOW,favtv);
                params.put(Config.KEY_FAV_MOVIE,favmovie);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddFavorite ae = new AddFavorite();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addFavorite();
        }

        if(v == buttonView){
            startActivity(new Intent(this,ViewAllFavUser.class));
        }

        if(v == buttonViewFav){
            startActivity(new Intent(this, ShowFavorites.class));
        }
    }
}

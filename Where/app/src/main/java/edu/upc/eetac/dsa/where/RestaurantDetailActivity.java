package edu.upc.eetac.dsa.where;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.where.Client.Entity.Restaurant;
import edu.upc.eetac.dsa.where.Client.WhereClient;
import edu.upc.eetac.dsa.where.Client.WhereClientException;

/**
 * Created by Carolina on 18/01/2016.
 */
public class RestaurantDetailActivity extends AppCompatActivity {

    GetRestaurantTask mGetRestaurantTask = null;
    String uri = null;
    String name = null;
    String id = null;
    int likes =0 ;
    String address = null;
    String owner = null;
    String description = null;
    String phone = null;
    private final static String TAG = RestaurantDetailActivity.class.toString();

    TextView textViewID = null;
    TextView textViewName= null;
    TextView textViewDescription= null;
    TextView textViewOwner= null ;
    TextView textViewLikes= null ;
    TextView textViewaddress= null ;
    TextView textViewPhone= null;


    class GetRestaurantTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetRestaurantTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonSting = null;
            try {
                jsonSting = WhereClient.getInstance().getRestaurants(uri);
            } catch (WhereClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonSting;
        }

        @Override
        protected void onPostExecute(String jsonRestaurant) {
            Log.d(TAG, jsonRestaurant);
            Restaurant restaurant = (new Gson()).fromJson(jsonRestaurant, Restaurant.class);

            id=restaurant.getId();
            name=restaurant.getName();
            description=restaurant.getDescription();
            owner= restaurant.getOwner();
            likes= restaurant.getLikes();
            address= restaurant.getAddress();
            phone = restaurant.getPhone();

            textViewID.setText(id);
            textViewName.setText(name);
            textViewDescription.setText(description);
            textViewOwner.setText(owner);
            textViewLikes.setText(likes);
            textViewaddress.setText(address);
            textViewPhone.setText(phone);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        uri = (String) getIntent().getExtras().get("uri");

        textViewID= (TextView) findViewById(R.id.textViewId);
        textViewName= (TextView) findViewById(R.id.textViewName);
        textViewDescription= (TextView) findViewById(R.id.textViewDescription);
        textViewOwner= (TextView) findViewById(R.id.textViewOwner);
        textViewLikes= (TextView) findViewById(R.id.textViewLikes);;
        textViewaddress= (TextView) findViewById(R.id.textViewAddress);
        textViewPhone= (TextView) findViewById(R.id.textViewPhone);


        // Execute AsyncTask
        mGetRestaurantTask = new GetRestaurantTask(uri);
        mGetRestaurantTask.execute((Void) null);


    }
}


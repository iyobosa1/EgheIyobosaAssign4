//Eghe Iyobosa N01107171 Section RNB
package eghe.iyobosa.n01107171.ei;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NguyenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFrag newInstance(String param1, String param2) {
        WeatherFrag fragment = new WeatherFrag();
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
    EditText editTextNumberDecimal;
    TextView txtDisplayZip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.weather_frag, container, false);
        editTextNumberDecimal =  view.findViewById(R.id.editTextNumberDecimal);
        txtDisplayZip = view.findViewById(R.id.txtDisplayZip);
        new ReadJSONFeedTask().execute(
                "http://extjs.org.cn/extjs/examples/grid/survey.html");
        return view;
    }
    public void getWeather(View view)
    {

        String txt = editTextNumberDecimal.getText().toString();

        //get weather information using geo coordinates
        //this method calls OpenWeatherMap API
        //
        //create the URL to call JSON service
        //"http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=13f04464b7119837cf1dc4fa8b39caa3");

        String url = "https://samples.openweathermap.org/data/2.5/weather?zip=";
        url+=txt;
        url+=",us&appid";
        Log.d("URL",url);
        new ReadJSONFeedTask().execute(url);
        //new ReadJSONFeedTask().execute(
        //        "https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=13f04464b7119837cf1dc4fa8b39caa3");


    }


    public String readJSONFeed(String address) {
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        };
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream content = new BufferedInputStream(
                    urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return stringBuilder.toString();
    }
    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }
        protected void onPostExecute(String result) {
            try {
                //JSONArray jsonArray = new JSONArray(result);
                //Uncomment the two rows below to parse weather data from OpenWeatherMap
                JSONObject zipJson = new JSONObject(result);
                JSONArray dataArray1= zipJson.getJSONArray("zip");
                String strResults="Address\n";
                for (int i = 0; i < dataArray1.length(); i++) {
                    JSONObject jsonObject = dataArray1.getJSONObject(i);
                    strResults +="coord: "+jsonObject.getString("coord");
                    strResults +="\nmain: "+jsonObject.getString("main");
                    strResults +="\nname: "+jsonObject.getString("name");
                    strResults +="\ncod: "+jsonObject.getString("code");
                }
                //
                JSONObject dataObject= zipJson.getJSONObject("main");
                strResults +="\ntemp: "+dataObject.getString("temp");
                strResults +="\nhumidity: "+dataObject.getString("humidity");
                strResults +="\ntemp_min: "+dataObject.getString("temp_min");
                strResults +="\ntemp_max: "+dataObject.getString("temp_max");
                //
                txtDisplayZip.setText(strResults);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
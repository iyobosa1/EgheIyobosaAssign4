//Eghe Iyobosa N01107171 Section RNB

package eghe.iyobosa.n01107171.ei;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;


public class DownloadFrag extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    int mDate, mMonth, mYear;


    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView= null;
    ProgressDialog p;
    Handler handle;
    public DownloadFrag() {
        // Required empty public constructor
    }


    public static DownloadFrag newInstance(String param1, String param2) {
        DownloadFrag fragment = new DownloadFrag();
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

        View view = inflater.inflate(R.layout.download_frag, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataBase", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Calendar calendar = Calendar.getInstance();
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.courses,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = view.findViewById(R.id.date);
        textViewDate.setText(currentDate);

        Button button=view.findViewById(R.id.asyncTask);
        imageView=view.findViewById(R.id.image);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AsyncTaskExample asyncTask = new AsyncTaskExample();
                asyncTask.execute("https://i.pinimg.com/originals/d5/4d/d3/d54dd30d97cfe54fa2710e640a6b1652.jpg");
            }

        });return view;
    }
    @Deprecated
    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {
        Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                p.incrementProgressBy(50); // Incremented By Value 2
            }
        };
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            p=new ProgressDialog(getActivity());
            p.setMax(100);

            p.setIndeterminate(false);
            p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            p.setCancelable(false);
            p.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (p.getProgress() <= p.getMax()) {
                            Thread.sleep(200);
                            handle.sendMessage(handle.obtainMessage());
                            if (p.getProgress() == p.getMax()) {
                                p.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {

                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }
        }
    }

}
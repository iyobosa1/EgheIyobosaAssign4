//Eghe Iyobosa N01107171 Section RNB
package eghe.iyobosa.n01107171.ei;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.ComponentCallbacks;

public class SettingsFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITR
    //    private static final String ARG_PARAM1 = "param1";EM_NUMBE
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioButton radioButton;
    RadioGroup radioGroup;
    Switch aSwitch;
    int s=0;
    String bg="";
    Boolean sw=true;
    //Seek bar object
    private SeekBar seekBar;

    //Variable to store brightness value
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;
    public SettingsFrag() {
        // Required empty public constructor
    }

    public static SettingsFrag newInstance(String param1, String param2) {
        SettingsFrag fragment = new SettingsFrag();
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
        final View view = inflater.inflate(R.layout.settings_fragment,container, false);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        int cBrightness = Settings.System.getInt(getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,0);
        seekBar.setProgress(cBrightness);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkButton(view);
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataBase", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        aSwitch = view.findViewById(R.id.sw);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sw = true;
                }else{
                    sw = false;
                }
            }
        });
        final Button apply = view.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply(view);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                s = seekBar.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }
    public  void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(radioId);
        bg = radioButton.getText().toString();

    }
    public void apply(View v){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataBase", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("seekbar", s);
        editor.putString("bachground", bg);
        editor.putBoolean("orientation",sw);
        editor.commit();
        LinearLayout ln = v.findViewById(R.id.fragment);
        if(bg.equals("Green")) {
            ln.setBackgroundColor(Color.GREEN);
        }else if(bg.equals("Red")){
            ln.setBackgroundColor(Color.RED);
        }else if(bg.equals("Blue")){
            ln.setBackgroundColor(Color.BLUE);
        }else{
            ln.setBackgroundColor(Color.WHITE);

        }
        Context context = getContext();
        boolean canWrite = Settings.System.canWrite(context);
        if (canWrite){

            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, s);
        }else{
            Intent intent = new Intent (Settings.ACTION_MANAGE_WRITE_SETTINGS);
            context.startActivity(intent);
        }
//
    }
}
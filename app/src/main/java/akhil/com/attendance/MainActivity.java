package akhil.com.attendance;import android.annotation.SuppressLint;import android.content.Context;import android.content.Intent;import android.net.ConnectivityManager;import android.net.NetworkInfo;import android.net.Uri;import android.os.Bundle;import android.os.Handler;import android.view.MotionEvent;import android.view.View;import android.view.Window;import android.view.WindowManager;import android.view.inputmethod.InputMethodManager;import android.widget.CompoundButton;import android.widget.RelativeLayout;import android.widget.Toast;import com.google.android.material.textfield.TextInputLayout;import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.widget.AppCompatButton;import androidx.appcompat.widget.AppCompatCheckBox;public class MainActivity extends AppCompatActivity {    RelativeLayout rellay1, rellay2;    AppCompatButton login_button,website_button;    private TextInputLayout userName, password;    AppCompatCheckBox rememberMe;    boolean remember=false;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        // In Activity's onCreate() for instance        Window w = getWindow();        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);        handler.postDelayed(runnable, 1500); //2000 is the timeout for the splash        findViewById(R.id.relative_layout).setOnTouchListener(new View.OnTouchListener() {            @Override            public boolean onTouch(View v, MotionEvent event) {                userName.setError(null);                password.setError(null);                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);                return true;            }        });        userName=(TextInputLayout)findViewById(R.id.user_TextInputLayout);        password=(TextInputLayout)findViewById(R.id.password_TextInputLayout);        login_button=(AppCompatButton)findViewById(R.id.login_button);        login_button.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if(isConnected()){                    String userString=userName.getEditText().getText().toString();                    String passwordString=password.getEditText().getText().toString();                    if (userString.isEmpty()&&passwordString.isEmpty()){                        userName.setError("Username is invalid");                        password.setError("Password is invalid");                    }                    else if(userString.isEmpty()){                        userName.setError("Username is invalid");                        password.setError(null);                    }                    else if(passwordString.isEmpty())                    {                        userName.setError(null);                        password.setError("Password is invalid");                    }                    else{                        userName.setError(null);                        password.setError(null);                        openResultactivity(userString,passwordString);                    }                }                else                    Toast.makeText(MainActivity.this, "Internet connection required", Toast.LENGTH_LONG).show();            }        });        website_button=(AppCompatButton)findViewById(R.id.website_button);        website_button.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Uri uriUrl = Uri.parse("https://erp.cbit.org.in/");                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);                startActivity(launchBrowser);            }        });        rememberMe=(AppCompatCheckBox)findViewById(R.id.remember_me);        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {                if(isChecked){                    remember=true;                }                else{                    remember=false;                }            }        });    }    Handler handler = new Handler();    Runnable runnable = new Runnable() {        @Override        public void run() {            rellay1.setVisibility(View.VISIBLE);            rellay2.setVisibility(View.VISIBLE);        }    };    public void openResultactivity(String userName, String Password){        Intent intent=new Intent(this, WaitingActivity.class);        intent.putExtra("Username",userName);        intent.putExtra("Password",Password);        intent.putExtra("Remember",remember);        startActivity(intent);    }    public boolean isConnected(){        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);        @SuppressLint("MissingPermission") NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();        return networkInfo!=null && networkInfo.isConnected();    }}
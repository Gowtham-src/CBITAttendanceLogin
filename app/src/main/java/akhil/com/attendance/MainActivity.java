package akhil.com.attendance;import android.annotation.SuppressLint;import android.content.ActivityNotFoundException;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;import android.net.ConnectivityManager;import android.net.NetworkCapabilities;import android.net.NetworkInfo;import android.net.Uri;import android.os.Build;import android.os.Bundle;import android.os.Handler;import android.view.MotionEvent;import android.view.View;import android.view.Window;import android.view.WindowManager;import android.view.inputmethod.InputMethodManager;import android.widget.CompoundButton;import android.widget.RelativeLayout;import android.widget.Toast;import com.google.android.material.textfield.TextInputLayout;import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.widget.AppCompatButton;import androidx.appcompat.widget.AppCompatCheckBox;import androidx.appcompat.widget.AppCompatEditText;public class MainActivity extends AppCompatActivity {    RelativeLayout rellay1, rellay2,rellay3;    AppCompatButton login_button,website_button,syllabusButton,notesButton;    String userString,passwordString;    private TextInputLayout userName, password;    private AppCompatEditText editUsername,editPassword;    boolean remember=false;    private AppCompatCheckBox saveLoginCheckBox;    private SharedPreferences loginPreferences;    private SharedPreferences.Editor loginPrefsEditor;    private Boolean saveLogin;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        // In Activity's onCreate() for instance        Window w = getWindow();        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);        rellay3 = (RelativeLayout) findViewById(R.id.rellay3);        handler.postDelayed(runnable, 750); //2000 is the timeout for the splash        findViewById(R.id.relative_layout).setOnTouchListener(new View.OnTouchListener() {            @Override            public boolean onTouch(View v, MotionEvent event) {                userName.setError(null);                userName.setErrorEnabled(false);                password.setError(null);                password.setErrorEnabled(false);                View focusedView = getCurrentFocus();                if(v!=null&&focusedView!=null){                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);                    if (imm != null) {                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);                    }                }                return true;            }        });        userName=(TextInputLayout)findViewById(R.id.user_TextInputLayout);        password=(TextInputLayout)findViewById(R.id.password_TextInputLayout);        editUsername=(AppCompatEditText)findViewById(R.id.user_TextField);        editPassword=(AppCompatEditText)findViewById(R.id.password_TextField);        saveLoginCheckBox = (AppCompatCheckBox) findViewById(R.id.remember_me);        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);        loginPrefsEditor = loginPreferences.edit();        saveLogin = loginPreferences.getBoolean("saveLogin", false);        if (saveLogin.equals(true)) {            editUsername.setText(loginPreferences.getString("username", ""));            editPassword.setText(loginPreferences.getString("password", ""));            saveLoginCheckBox.setChecked(true);            if (isConnected()){                Intent intent=new Intent(this, WaitingActivity.class);                intent.putExtra("Username",loginPreferences.getString("username", ""));                intent.putExtra("Password",loginPreferences.getString("password", ""));                intent.putExtra("Remember",remember);                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                startActivity(intent);            }            else{                Toast.makeText(MainActivity.this, "Internet connection required", Toast.LENGTH_LONG).show();            }        }        login_button=(AppCompatButton)findViewById(R.id.login_button);        login_button.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if(isConnected()){                    userString=editUsername.getText().toString();                    passwordString=editPassword.getText().toString();                    if (userString.isEmpty()&&passwordString.isEmpty()){                        userName.setErrorEnabled(true);                        userName.setError("Username is invalid");                        password.setErrorEnabled(true);                        password.setError("Password is invalid");                    }                    else if(userString.isEmpty()){                        userName.setErrorEnabled(true);                        userName.setError("Username is invalid");                        password.setError(null);                        password.setErrorEnabled(false);                    }                    else if(passwordString.isEmpty())                    {                        userName.setError(null);                        userName.setErrorEnabled(false);                        password.setErrorEnabled(true);                        password.setError("Password is invalid");                    }                    else{                        userName.setError(null);                        userName.setErrorEnabled(false);                        password.setError(null);                        password.setErrorEnabled(false);                        if (saveLoginCheckBox.isChecked()) {                            loginPrefsEditor.putBoolean("saveLogin", true);                            loginPrefsEditor.putString("username", userString);                            loginPrefsEditor.putString("password", passwordString);                            loginPrefsEditor.apply();                        }                        else {                            loginPrefsEditor.clear();                            loginPrefsEditor.commit();                        }                        openResultactivity(userString,passwordString);                    }                }                else                    Toast.makeText(MainActivity.this, "Internet connection required", Toast.LENGTH_LONG).show();            }        });        website_button=(AppCompatButton)findViewById(R.id.website_button);        website_button.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Uri uriUrl = Uri.parse("https://erp.cbit.org.in/");                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);                startActivity(launchBrowser);            }        });        syllabusButton=(AppCompatButton)findViewById(R.id.syllabus_button);        syllabusButton.setOnLongClickListener(new View.OnLongClickListener() {            public boolean onLongClick(View arg0) {                Toast.makeText(getApplicationContext(), "Syllabus Book" ,Toast.LENGTH_SHORT).show();                return true;            }        });        notesButton=(AppCompatButton)findViewById(R.id.notes_button);        notesButton.setOnLongClickListener(new View.OnLongClickListener() {            public boolean onLongClick(View arg0) {                Toast.makeText(getApplicationContext(), "Notes Section" ,Toast.LENGTH_SHORT).show();                return true;            }        });        saveLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {                if(isChecked){                    remember=true;                }                else{                    remember=false;                }            }        });    }    Handler handler = new Handler();    Runnable runnable = new Runnable() {        @Override        public void run() {            rellay1.setVisibility(View.VISIBLE);            rellay2.setVisibility(View.VISIBLE);            rellay3.setVisibility(View.VISIBLE);        }    };    public void openResultactivity(String userName, String Password){        Intent intent=new Intent(this, WaitingActivity.class);        intent.putExtra("Username",userName);        intent.putExtra("Password",Password);        intent.putExtra("Remember",remember);        startActivity(intent);    }    public void rateMe(View v) {        try {            startActivity(new Intent(Intent.ACTION_VIEW,                    Uri.parse("market://details?id=" + getPackageName())));        } catch (ActivityNotFoundException e) {            startActivity(new Intent(Intent.ACTION_VIEW,                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));        }    }    public void syllabus(View v) {        Intent intent=new Intent(MainActivity.this, SyllabusActivity.class);        startActivity(intent);    }    public void notes(View v) {        Intent intent=new Intent(MainActivity.this, NotesActivity.class);        startActivity(intent);    }    /*public boolean isConnected(){        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);        if (connectivityManager != null) {            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());                if (capabilities != null) {                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {                        return true;                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {                        return true;                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){                        return true;                    }                }            }            else {                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();                return networkInfo!=null && networkInfo.isConnected();            }        }        return false;    }*/    public boolean isConnected(){        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);        NetworkInfo networkInfo= null;        if (connectivityManager != null) {            networkInfo = connectivityManager.getActiveNetworkInfo();        }        return networkInfo!=null && networkInfo.isConnected();    }}
package com.example.loginandsplashscreen.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandsplashscreen.JsonedClasses.Customer;
import com.example.loginandsplashscreen.Handlers.NetworkHandling;
import com.example.loginandsplashscreen.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private DrawerLayout mDrawerLayout2;
    Customer o = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkConnectivity();
        System.out.println("Token in MainActivity: " + LoginActivity.token);
        try {
            checkToken();
        } catch (Exception e) {
            System.out.println(e);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px); // sets the toggle button icon
        mDrawerLayout = findViewById(R.id.drawer);
        //mDrawerLayout2 = findViewById(R.id.drawer2);
        NavigationView navigationView = findViewById(R.id.nav_menu);
        try {
            o = new Gson().fromJson(new NetworkHandling().execute("getInfo",LoginActivity.token).get(), Customer.class);
        } catch (Exception e) {
            System.out.println(e);
        }

        BottomNavigationView navigationView2=findViewById(R.id.bottom_nav);
        final BagisFragment bagisFragment=new BagisFragment();
        final EmpfehlenFragment empfehlenFragment =new EmpfehlenFragment();
        final ParagonderFragment paragonderFragment =new ParagonderFragment();
        final OdemeFragment odemeFragment = new OdemeFragment();


        setFragment(paragonderFragment);

        /*Button m1showDialog=(Button) findViewById(R.id.bu_paragonder_send);
        m1showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View mview=getLayoutInflater().inflate(R.layout.dialog_paragonder, null);
                Button button =(Button) mview.findViewById(R.id.paragonder_evet);
                Button button1=(Button) mview.findViewById(R.id.paragonder_hayir);
                Button button2=(Button) mview.findViewById(R.id.paragonder_iptal);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Para gönderildi.",Toast.LENGTH_SHORT ).show();
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Para gönderilmedi.",Toast.LENGTH_SHORT ).show();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "İşlem iptal edildi.",Toast.LENGTH_SHORT ).show();
                    }
                });

            }
        });

        Button m2showDialog=(Button) findViewById(R.id.bu_empfehlen_onayla);
        m2showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View mview=getLayoutInflater().inflate(R.layout.dialog_empfehlen, null);
                Button button =(Button) mview.findViewById(R.id.oner_evet);
                Button button1=(Button) mview.findViewById(R.id.oner_hayir);
                Button button2=(Button) mview.findViewById(R.id.oner_iptal);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Kişi önerildi.",Toast.LENGTH_SHORT ).show();
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Kişi önerilmedi.",Toast.LENGTH_SHORT ).show();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "İşlem iptal edildi.",Toast.LENGTH_SHORT ).show();
                    }
                });

            }
        });*/

        navigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.bagis){
                    setFragment(bagisFragment);
                    return true;
                }else if(id==R.id.oner){
                    setFragment(empfehlenFragment);
                    return true;
                }else if(id == R.id.paragonder){
                    setFragment(paragonderFragment);
                    return true;
                } else if (id == R.id.odeme) {
                    setFragment(odemeFragment);
                    return true;
                } else {
                    return false;
                }
            }
        });

        //Defines what will happen then a menu item is selected, when an item is selected, the SideBarMenu will be closed.
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        int d = menuItem.getItemId();
                        switch (d) {
                            case R.id.ayarlar:
                                Intent intent5 = new Intent(MainActivity.this, SettingsActivity.class);
                                finish();
                                startActivity(intent5);
                                break;
                            /*case R.id.yemeklistesi:
                                final YemekListeFragment yemekListeFragment = new YemekListeFragment();
                                setFragment(yemekListeFragment);
                                Intent intent = new Intent(MainActivity.this,YemekListeActivity.class);
                                break;*/
                            case R.id.feedback:
                                Intent intent2 = new Intent(MainActivity.this, FeedbackActivity.class);
                                startActivity(intent2);
                                finish();
                                break;
                        }

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


        //Listens the SideBarMenu(Drawer) actions
        mDrawerLayout.addDrawerListener(

                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);

                        TextView t = drawerView.findViewById(R.id.user_name);
                    try {
                        //TODO: Tell backend to round off the numbers to two commas
                        t.setText(o.getName());
                        t = drawerView.findViewById(R.id.ogrenci_numarasi);
                        t.setText(o.getId());
                        t = drawerView.findViewById(R.id.yemek_bakiye);
                        t.setText(o.getBalanceMensa() + " TL");
                        t = drawerView.findViewById(R.id.shuttle_bakiye);
                        t.setText(o.getBalanceShuttle() + " TL");
                    } catch (Exception e) {
                        System.out.println(e);
                        finish();
                    }

                        // As a respond when the drawer is opened, sets back arrow icon
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
                        // As a respond when the drawer is closed, sets Menu icon
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

    }

    public void checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED)) {
            logout();
        }
    }

    public void logout() {
        final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    public void checkToken() throws Exception {
        SharedPreferences preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        LoginActivity.token = preferences.getString("var1",LoginActivity.token);
        if (!isInfoValid(LoginActivity.token)) {
            logout();
        }
    }

    private boolean isInfoValid(String token) throws Exception{
        NetworkHandling h = new NetworkHandling();
        String k = h.execute("getInfo",token).get();
        return k.charAt(0) == '{';
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


    /*@Override
    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_settings);

        if(item != null){
            item.getActionView().setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    System.out.println("hallohallo");
                }
            });
        }
        return true;
    }*/

    //Open drawer when the button is tapped
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

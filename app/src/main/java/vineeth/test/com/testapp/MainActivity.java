package vineeth.test.com.testapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int permission=1;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("test","Inside on create");
        setContentView(R.layout.tool_bar_layout);
       /* final Button redirect=findViewById(R.id.redirect);
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirect();
            }
        });

        final Button finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setListView();

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},permission);
        }
    }

    public void onStart()
    {
        super.onStart();
        Log.d("test","Inside on start");
    }

    public void onResume()
    {
        super.onResume();
        Log.d("test","Inside onResume");
    }

    public void onPause()
    {
        super.onPause();
        Log.d("test","Inside onPause");
    }

    public void onStop()
    {
        super.onStop();
        Log.d("test","Inside onStop");
    }

    public void onRestart()
    {
        super.onRestart();
        Log.d("test","Inside onRestart");
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d("test","Inside onDestroy");
    }

    public void redirect()
    {
       Intent intent = new Intent(this,RedirectActivity.class);
       startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
       getMenuInflater().inflate(R.menu.main_activity,menu);
       return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
       Toast.makeText(MainActivity.this,"item id"+item.getItemId(),Toast.LENGTH_SHORT).show();
       return true;
    }

    private void setListView()
    {
        ListView listView = findViewById(R.id.list_view);
        String[] items = new String[]{"Pavani","Vineeth","Anil"};
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
      super.onCreateContextMenu(menu,v,menuInfo);
      getMenuInflater().inflate(R.menu.main_activity,menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        Toast.makeText(MainActivity.this,"item id"+item.getItemId(),Toast.LENGTH_SHORT).show();
        return true;
    }

    public void onRequestPermissionsResult(int request,String[] permissions,int[] grantResult)
    {
       if(grantResult[0]==PackageManager.PERMISSION_GRANTED)
       {

       }
    }
}

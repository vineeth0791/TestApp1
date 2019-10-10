package vineeth.test.com.testapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int permission=1;
    private String string1;
    private String string2;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("test","Inside on create");
        setContentView(R.layout.tool_bar_layout);
        string1="string";
        string2="string";
        Log.d("MainActivity","String1 is before"+string1+"refer"+string1.hashCode());
        Log.d("MainActivity","String2 is before"+string2+"refer"+string2.hashCode());
        string1 = string1.concat(" string1");
        Log.d("MainActivity","String1 is after"+string1+string1.hashCode());
        Log.d("MainActivity","String2 is after"+string2+string2.hashCode());

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

package vineeth.test.com.testapp.material_design_ex.bottom_sheets;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vineeth.test.com.testapp.R;

public class PersistantBottomSheetsEx extends AppCompatActivity {
  private Context context;
    public void onCreate(Bundle savedInstaceState)
    {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.persistant_main);
        context=PersistantBottomSheetsEx.this;
        LinearLayout bottomSheetLayout = findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        findViewById(R.id.btn_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED)
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }*/
                //displayModelSheetUsingDialog();
                displayUsingFragment();
            }
        });
/*
        RecyclerView recyclerView=findViewById(R.id.list_view);
        String[] items=new String[]{"movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy"};
        RecyclerAdapter adapter=new RecyclerAdapter(context,items);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(recyclerView);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(behavior.getState()==BottomSheetBehavior.STATE_COLLAPSED)
                {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else
                {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });*/
    }

    private void displayModelSheetUsingDialog()
    {
        BottomSheetDialog bsd = new BottomSheetDialog(context);
        bsd.setContentView(R.layout.persistant_bottom_sheet);

        bsd.show();
    }

    private void displayUsingFragment()
    {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key","value");
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),fragment.getTag());
    }
}

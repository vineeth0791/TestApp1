package vineeth.test.com.testapp.room_db_ex;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vineeth.test.com.testapp.R;
import vineeth.test.com.testapp.databinding.UserListBinding;
import vineeth.test.com.testapp.kotlin_db_entity.User;

public class DisplayUserDetailsAdapter extends RecyclerView.Adapter<DisplayUserDetailsAdapter.MyViewHolder> {
    private List<User> users;
    private LayoutInflater inflator;

    public DisplayUserDetailsAdapter(List<User> users)
    {
        this.users = users;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        UserListBinding binding;
        public MyViewHolder(UserListBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent,int type)
    {
        if(inflator==null)
            inflator = LayoutInflater.from(parent.getContext());
        UserListBinding userListBinding = DataBindingUtil.inflate(inflator,R.layout.user_list,parent,false);

       return new MyViewHolder(userListBinding);
    }

    public void onBindViewHolder(MyViewHolder viewHolder,int position)
    {
      viewHolder.binding.setUser(users.get(position));
    }

    public int getItemCount()
    {
        return users.size();
    }
}

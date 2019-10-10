package vineeth.test.com.testapp.socket_prog;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.net.Socket;

import static vineeth.test.com.testapp.socket_prog.ServerClass.PORT_NUMBER;

public class ClientClass extends AppCompatActivity {

    private Context context;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context= ClientClass.this;
        new Thread(new CreateClient()).start();
    }

    private class CreateClient extends Thread{
        public void run()
        {
            createClient();
        }
    }

    private void createClient()
    {
        try {
            Socket socket = new Socket("192.168.0.100", PORT_NUMBER);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("Hi hello");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"socket established",Toast.LENGTH_SHORT).show();
                }
            });
            socket.close();

            //socket.connect();
        }catch(final Exception ex)
        {
            ex.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"Error in createding server "+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}

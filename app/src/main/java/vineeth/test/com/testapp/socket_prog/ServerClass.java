package vineeth.test.com.testapp.socket_prog;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass extends AppCompatActivity {

    public final static int PORT_NUMBER=8282;
    private Context context;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = ServerClass.this;
        Thread thread = new Thread(new CreateServerSocket());
        thread.start();
    }

    private class CreateServerSocket extends Thread{
        public void run()
        {
            createServerSocket();
        }
    }

    private void createServerSocket()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

            Socket clientSocket = serverSocket.accept();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Client is connected", Toast.LENGTH_SHORT).show();
                }
            });

            final DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            final String clientMsg = (String) dis.readUTF();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(context,clientMsg , Toast.LENGTH_SHORT).show();
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                        Toast.makeText(context, "Eror "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            serverSocket.close();

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

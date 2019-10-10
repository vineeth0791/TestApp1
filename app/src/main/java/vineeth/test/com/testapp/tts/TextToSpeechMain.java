package vineeth.test.com.testapp.tts;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vineeth.test.com.testapp.R;

public class TextToSpeechMain extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech tts;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tts_main);
        tts = new TextToSpeech(this,this);

    }

    public void onInit(int status)
    {
        if(status==TextToSpeech.SUCCESS)
        {
            Toast.makeText(this,"TTS engine has been initialized successfully",Toast.LENGTH_SHORT).show();
            tts.setPitch(20.0f);
            speak();

        }else
        {
            Toast.makeText(this,"Unable to initialize tts engine",Toast.LENGTH_SHORT).show();
        }
    }

    public void speak()
    {
        Button speakButton = findViewById(R.id.speak);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText textET = findViewById(R.id.text);
                String textString = textET.getText().toString();
                tts.speak(textString,TextToSpeech.QUEUE_ADD,null);
            }
        });
    }
}

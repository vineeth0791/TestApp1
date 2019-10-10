package vineeth.test.com.testapp.speech_to_text;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SpeechRecgService extends Service implements RecognitionListener {
   private SpeechRecognizer speech;
   private Context context;
   String TAG="SpeechRecg";
   Intent recognizerIntent;
    public void onCreate()
    {
        context=SpeechRecgService.this;
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int startId)
    {
        startSpeechRecg();
        initRecgIntent();
        speech.startListening(recognizerIntent);
        return START_NOT_STICKY;
    }

    private void initRecgIntent()
    {


        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


    }

    private void startSpeechRecg()
    {
        if(speech!=null)
            speech.destroy();

        speech = SpeechRecognizer.createSpeechRecognizer(context);
        if(!SpeechRecognizer.isRecognitionAvailable(context))
        {
            Toast.makeText(context,"Speech recognization service not available",Toast.LENGTH_SHORT).show();
            stopSelf();
            return;
        }

        speech.setRecognitionListener(this);


    }

    public void onReadyForSpeech(Bundle values)
    {
        Log.d(TAG,"Inside on ready for speech");
    }

    public  void onBeginningOfSpeech()
    {
        Log.d(TAG,"Inside on beginning of speech");
    }

    public void onRmsChanged(float var1)
    {
       // Log.d(TAG,"Inside on onRmsChanged - "+var1);
    }

    public void onBufferReceived(byte[] var1)
    {
        Log.d(TAG,"Inside on onBufferReceived - "+var1);
    }



    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(TAG,"Inside on onEndOfSpeech - "+errorMessage);
        // rest voice recogniser
        startSpeechRecg();
        speech.startListening(recognizerIntent);
    }

    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    public void onEndOfSpeech()
    {
        Log.d(TAG,"Inside on onEndOfSpeech - ");
        speech.stopListening();
    }

    @Override
    public void onResults(Bundle results) {

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

        Log.i(TAG, "Inside onResults-"+text);
        speech.startListening(recognizerIntent);
    }

    public void onPartialResults(Bundle results)
    {
        Log.i(TAG, "Inside onPartialResults" );
    }

    public void onEvent(int event,Bundle values)
    {
        Log.d(TAG,"Inside on event "+event);
    }

}

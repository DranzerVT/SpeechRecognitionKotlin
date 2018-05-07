package com.jarvis.speechrecognitionkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.jarvis.speechrecognitionkotlin.Interfaces.SpeechResults

import java.util.ArrayList

/**
 * Created by user on 9/11/2017.
 */

class SpeechRecognitionListener( context: MainActivity, internal var mSpeechRecognizer: SpeechRecognizer, internal var mSpeechRecognizerIntent: Intent) : RecognitionListener {

    internal var context: Context
    internal var txtSpeechInput: EditText
    internal var speechResults: SpeechResults

    init {
        this.context = context
        speechResults = context
        txtSpeechInput = context.findViewById<EditText>(R.id.txtSpeechInput)
    }

    override fun onReadyForSpeech(params: Bundle) {

    }

    override fun onBeginningOfSpeech() {


        txtSpeechInput.setText("")
    }

    override fun onRmsChanged(rmsdB: Float) {

    }

    override fun onBufferReceived(buffer: ByteArray) {

    }

    override fun onEndOfSpeech() {

    }

    override fun onError(error: Int) {

        Log.e("onError: ", "In Error")
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
    }

    override fun onResults(results: Bundle) {

        //Log.d(TAG, "onResults"); //$NON-NLS-1$
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        txtSpeechInput.setText(matches!![0])

        // matches are the return values of speech recognition engine
        // Use these values for whatever you wish to do

        speechResults.onSpeechResult(matches[0])
    }

    override fun onPartialResults(partialResults: Bundle) {

    }

    override fun onEvent(eventType: Int, params: Bundle) {

    }
}

package com.jarvis.speechrecognitionkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.jarvis.speechrecognitionkotlin.Interfaces.SpeechResults
import com.jarvis.speechrecognitionkotlin.ModelClasses.Message
import java.util.*
import com.plattysoft.leonids.ParticleSystem
import com.jarvis.speechrecognitionkotlin.Adapters.MessageListAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.widget.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(),SpeechResults  {


    private var txtSpeechInput : EditText? = null
    private var sendButton : Button ?= null
    private var btnSpeak : ImageButton? = null
    private val REQ_CODE_SPEECH_INPUT = 100

    private var mSpeechRecognizer: SpeechRecognizer? = null
    private var mSpeechRecognizerIntent: Intent? = null
    private var mIslistening: Boolean = false

    private var mMessageRecycler: RecyclerView? = null

    private var mMessageAdapter: MessageListAdapter? = null
    private var messageList : ArrayList<Message> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.message_main_screen)

        txtSpeechInput = findViewById<EditText>(R.id.txtSpeechInput)
        btnSpeak = findViewById<ImageButton>(R.id.btnSpeak)
        sendButton = findViewById<Button>(R.id.button_chatbox_send)

        mMessageRecycler = findViewById<RecyclerView>(R.id.reyclerview_message_list);


        mMessageAdapter = MessageListAdapter(this, messageList)

        mMessageRecycler?.setLayoutManager(LinearLayoutManager(this))
        mMessageRecycler?.adapter = mMessageAdapter



        val ab : ActionBar? = supportActionBar
        ab?.hide()

        if (PermissionHandler.checkPermission(this, PermissionHandler.RECORD_AUDIO)) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            mSpeechRecognizerIntent?.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            mSpeechRecognizerIntent?.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    this.packageName)


            var listener = SpeechRecognitionListener(this@MainActivity,
                    mSpeechRecognizer?: return
                    , mSpeechRecognizerIntent?: return)

            mSpeechRecognizer?.setRecognitionListener(listener)

            btnSpeak?.setOnClickListener(View.OnClickListener {
                Log.e("onClick: mIslistening ", mIslistening.toString())
                if (!mIslistening) {
                    mSpeechRecognizer?.startListening(mSpeechRecognizerIntent)


                    /*var particle : ParticleSystem = ParticleSystem(this,20,R.drawable.ic_mic_none,1000)
                    particle.setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
                            .setRotationSpeed(144.0f)
                            .setAcceleration(0.00005f, 90)
                            .emit(findViewById(R.id.btnSpeak), 8);*/

                }
            })
        } else {

            PermissionHandler.askForPermission(PermissionHandler.RECORD_AUDIO, this)
        }

        sendButton?.setOnClickListener(View.OnClickListener {

            var messageContent: String = txtSpeechInput?.text.toString()
           // var currentTime : Date = Calendar.getInstance().time

            if(!messageContent.equals("")) {

                var c: Calendar = Calendar.getInstance();
                var sdf = SimpleDateFormat("HH:mm:ss")

                var currentTime: String = sdf.format(c.time)

                var message = Message(messageContent, "SE", currentTime)

                messageList.add(message)

                mMessageAdapter?.notifyDataSetChanged()

                txtSpeechInput?.setText("")
            }

           /* mMessageAdapter = MessageListAdapter(this, messageList)
            mMessageRecycler?.adapter = mMessageAdapter
            mMessageRecycler?.setLayoutManager(LinearLayoutManager(this))*/


        })

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("destroy: mIslistening ", mIslistening.toString())
        mSpeechRecognizer?.destroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSpeechResult(result: String) {

        sendButton?.callOnClick()
       // Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }
}

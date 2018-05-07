package com.jarvis.speechrecognitionkotlin.Interfaces

/**
 * Created by user on 9/21/2017.
 */

interface SpeechResults {

    fun onSpeechResult(result: String)  // Resulting string recognized by Speech Recognizer
}

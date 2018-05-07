package com.jarvis.speechrecognitionkotlin.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.speechrecognitionkotlin.ModelClasses.Message
import com.jarvis.speechrecognitionkotlin.R
import kotlinx.android.synthetic.main.item_message_received.view.*
import kotlinx.android.synthetic.main.item_message_sent.view.*
import com.jarvis.speechrecognitionkotlin.Adapters.MessageListAdapter.SentMessageHolder
import com.jarvis.speechrecognitionkotlin.inflate


/**
 * Created by user on 11/18/2017.
 */
class MessageListAdapter(context : Context, internal var messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        var message : Message = messageList.get(position);

        when (holder?.getItemViewType()) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bindItems(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as RecievedMessageHolder).bindItems(message)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        val view: View
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
           // view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.item_message_sent, parent, false);
            view = parent.inflate(R.layout.item_message_sent,false)
            return SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
           // view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.item_message_received, parent, false);
            view = parent.inflate(R.layout.item_message_received,false)
            return RecievedMessageHolder(view)
        }

        return null
    }

    override fun getItemCount(): Int {

       return messageList.size
    }

    override fun getItemViewType(position: Int): Int {

        var message : Message = messageList.get(position);
        if(message.messagerType.equals("RE")){  //recieved message

            return VIEW_TYPE_MESSAGE_RECEIVED;
        }else{        //sent messages
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }


    class RecievedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(message : Message) = with(itemView){


            itemView.text_message_body_received.text = message.message
            itemView.text_message_time_received.text = message.messageDate as String
        }
    }

    class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(message : Message) = with(itemView){


            itemView.text_message_body_sent.text = message.message
            itemView.text_message_time_sent.text = message.messageDate as String
        }

    }
}
package practicaltest01var06.eim.systems.cs.pub.ro.practicaltest01var06;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.Random;

public class PracticalTest01Var06Service extends Service {
    public PracticalTest01Var06Service() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String link = intent.getStringExtra("link");

        ProcessingThread processingThread = new ProcessingThread(this, link);
        processingThread.start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class ProcessingThread extends Thread {

        private Context context;
        private String link;

        public ProcessingThread(Context context, String link) {
            this.context = context;
            this.link = link;
        }

        @Override
        public void run() {
            while (true) {
                sendMessage(1);
                sleep();
                sendMessage(2);
                sleep();
            }
        }

        private void sleep() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        private void sendMessage(int messageType) {
            Intent intent = new Intent();

            Calendar cal = Calendar.getInstance();

            switch(messageType) {
                case Constants.MESSAGE_1:
                    intent.setAction(Constants.ACTION_1);
                    break;
                case Constants.MESSAGE_2:
                    intent.setAction(Constants.ACTION_2);
                    break;

            }

            intent.putExtra("data", cal.get(Calendar.DATE) + " " + cal.get(Calendar.HOUR) + link + " ");
            context.sendBroadcast(intent);
        }

    }
}

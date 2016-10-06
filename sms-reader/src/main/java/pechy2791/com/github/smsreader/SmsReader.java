package pechy2791.com.github.smsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReader extends BroadcastReceiver {

    private static int mode = 0;

    private static int start = 0;

    private static int stop = 0;

    private static final String TAG = "SmsReader";

    private static SMSOTPListener SmsotpListener;

    private static String receiverString;

    public static void bind(SMSOTPListener listener, String sender) {
        SmsotpListener = listener;
        receiverString = sender;
        mode = 1;
    }

    public static void bind(SMSOTPListener listener, String sender,int a,int b) {
        SmsotpListener = listener;
        receiverString = sender;
        mode = 2;
        start = a;
        stop = b;
    }

    public static void bind(SMSOTPListener listener, String sender,String string,int b) {
        SmsotpListener = listener;
        receiverString = sender;
        mode = 3;
        start = string.length();
        stop = b;
    }

    public static void bind(SMSOTPListener listener, String sender,String a,String b) {
        SmsotpListener = listener;
        receiverString = sender;
        mode = 4;
        start = a.length()-1;
        stop = b.length()-1;
    }

    public static void bind(SMSOTPListener listener, String sender,String string) {
        SmsotpListener = listener;
        receiverString = sender;
        mode = 5;
        start = string.length()-1;
    }

    public static void bind(SMSOTPListener listener, String sender,int a) {
        SmsotpListener = listener;
        receiverString = sender;
        mode = 6;
        start = a;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {

            final Object[] pdusArr = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdusArr.length; i++) {

                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusArr[i]);
                String senderName = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();
                Log.i(TAG, "senderName: " + senderName + ", message: " + message);

                if (senderName.contains(receiverString)) {
                    if (SmsotpListener != null) {
                        switch (mode) {
                            case 1:
                                SmsotpListener.SmsOtpReceived(message);
                                break;
                            case 2:
                                SmsotpListener.SmsOtpReceived(message.substring(start,stop));
                                break;
                            case 3:
                                SmsotpListener.SmsOtpReceived(message.substring(start,stop));
                                break;
                            case 4:
                                SmsotpListener.SmsOtpReceived(message.substring(start,stop));
                                break;
                            case 5:
                                SmsotpListener.SmsOtpReceived(message.substring(start));
                                break;
                            case 6:
                                SmsotpListener.SmsOtpReceived(message.substring(start));
                                break;
                            default:
                                SmsotpListener.SmsOtpReceived("Error : Not Found Message.");
                                break;

                        }

                    }
                }
            }
        }
    }
}

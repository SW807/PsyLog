package dk.aau.cs.psylog.psylog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceHelper.startActiveServices(context);

        Intent taskRunnerIntent = new Intent(context, TaskRunner.class);
        context.startService(taskRunnerIntent);
    }
}

package consultan.vanke.com.log;

import com.dianping.logan.SendLogRunnable;

import java.io.*;

public class RealSendLogRunnable extends SendLogRunnable {
    @Override
    public void sendLog(File logFile) {
        //先上传
        //dosengdFileByAction(logFile);
        finish();
        if (logFile.getName().contains(".copy")) {
            logFile.delete();
        }
    }
}

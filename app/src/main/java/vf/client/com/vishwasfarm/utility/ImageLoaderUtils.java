package vf.client.com.vishwasfarm.utility;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by amhule on 12/1/2016.
 */
public class ImageLoaderUtils {
         public static void CopyStream(InputStream is, OutputStream os)
        {
            final int buffer_size=1024;
            try
            {

                byte[] bytes=new byte[buffer_size];
                for(;;)
                {
                    //Read byte from input stream

                    int count=is.read(bytes, 0, buffer_size);
                    if(count==-1)
                        break;

                    //Write byte from output stream
                    os.write(bytes, 0, count);
                }
            }
            catch(Exception ex){}
        }
    }

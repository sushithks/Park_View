/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingapp.qrcode;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Buffer;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Staff
 */
public class WebcamCaptureQrCode implements Runnable {

    Image img = null;
    FrameGrabbingControl fgc = null;
    Player player = null;

    public Result readQr() {
        Result result = null;
        try {
            String str = "vfw:Microsoft WDM Image Capture (Win32):0";
            MediaLocator mediaLocator = new MediaLocator(str);
            player = Manager.createPlayer(mediaLocator);
            player.start();
            Thread.sleep(3000);

            fgc = (FrameGrabbingControl) player.getControl(
                    "javax.media.control.FrameGrabbingControl");
            new Thread(this).start();
            //Webcam webcam = Webcam.getDefault(); // non-default (e.g. USB) webcam can be used too

            //webcam.open();
            while (true) {
                BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

//            if (webcam.isOpen()) {
//                if ((image = webcam.getImage()) == null) {
//                    System.out.println("dfsdf");
//                    //  continue;
//                }
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
                // }

                if (result != null) {
                    System.out.println("QR code data is: " + result.getText());
                }
            }
        } catch (NoClassDefFoundError er) {

        } catch (IOException ex) {
            Logger.getLogger(WebcamCaptureQrCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPlayerException ex) {
            Logger.getLogger(WebcamCaptureQrCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(WebcamCaptureQrCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public void run() {

        while (true) {
            try {
                
                Buffer buffer = fgc.grabFrame();
                BufferToImage bti = new BufferToImage((VideoFormat) buffer.getFormat());
                Image image = bti.createImage(buffer);
                getImage(image);
                player.close();
            } catch (NullPointerException ex) {
                //when there is no cam connected
                System.err.println("NULLL");
            }
        }
    }

    void getImage(Image image) {
        img = image;
    }
}

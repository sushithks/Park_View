
package com.parkingapp.qrcode;

import com.google.zxing.Result;

public class Testing {
    public static void main(String[] args) {
        Result readQr = new WebcamCaptureQrCode().readQr();
        if(readQr != null) {
        System.out.println("qr code is : " + readQr.getText());
        } else {
            System.out.println("no result");
        }
    }
}

package com.example.loginandsplashscreen.Handlers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage;

public class QRCodeHandler {


    //generate qr code image of given text
    public static Bitmap generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                bmp.setPixel(x, y, bitMatrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    //decode qr code image TODO: not used anymore
    /*public static String decodeQRCode(File qrCodeimage) throws IOException {
        //BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        Bitmap m = BitmapFactory.decodeFile(qrCodeimage.getPath());

        int a[] = new int[5];
        LuminanceSource source = new RGBLuminanceSource(400,400,a);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("Es konnte kein QR-Code in diesem Bild entziffert werden!");
            return null;
        }
    }*/
}


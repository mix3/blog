package org.mix3.blog.util;

import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.SeekableStream;

public class Resize {
    /**
     * 画像のリサイズ。
     * @param pixelCount ドット数
     * @param inputName 入力ファイル名
     * @param outputName 出力ファイル名
     * @param imageType 画像フォーマット。たとえば"JPEG"
     * @throws IOException 入出力処理時の例外
     */
    public byte[] resize(byte[] byteData) throws IOException {
        // 画像ファイルの読み込み
    	ByteArrayInputStream stream = new ByteArrayInputStream(byteData);
    	SeekableStream s = SeekableStream.wrapInputStream(stream, true);
        RenderedOp image1 = JAI.create("stream", s);

        // 画像の縮小
        Interpolation interp = Interpolation.getInstance(Interpolation.INTERP_BICUBIC_2);

        // 縮小比率の計算
//        double scale = 0.0;
//        double doubleValueWidth = (double)160 / image1.getWidth();
//        if (doubleValueWidth > 1)
//            doubleValueWidth = 1;
//        
//        double doubleValueHeight = (double)160 / image1.getHeight();
//        if (doubleValueHeight > 1)
//            doubleValueHeight = 1;
//
//        if (doubleValueHeight < doubleValueWidth)
//            scale = doubleValueHeight;
//        else
//            scale = doubleValueWidth;
        double scale = 0.0;
        double doubleValueWidth = (double)160 / image1.getWidth();
        double doubleValueHeight = (double)160 / image1.getHeight();

        if (doubleValueHeight < doubleValueWidth)
            scale = doubleValueHeight;
        else
            scale = doubleValueWidth;
        
        if(scale > 1)
        	return null;
        
        ParameterBlock params = new ParameterBlock();
        params.addSource(image1);
        params.add((float)scale);
        params.add((float)scale);
        params.add(0.0F);
        params.add(0.0F);
        params.add(interp);

        // 画像の縮小
        RenderedOp image2 = JAI.create("scale", params);

        // 画像の書き出し
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JAI.create("encode", image2, baos, "JPEG", null);
        return baos.toByteArray();
    }
    /**
     * ファイル名から拡張子を返します。
     * @param fileName ファイル名
     * @return ファイルの拡張子
     */
    public static String getSuffix(String fileName) {
        if (fileName == null)
            return null;
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }
        return fileName;
    }
}
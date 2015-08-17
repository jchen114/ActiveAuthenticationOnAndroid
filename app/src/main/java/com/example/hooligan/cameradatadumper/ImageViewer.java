package com.example.hooligan.cameradatadumper;

import java.io.File;

/**
 * Created by Hooligan on 8/13/2015.
 */
public class ImageViewer {

    public File mFile;
    public int X, Y, width, height;

    public ImageViewer (File imageFile, int X, int Y, int width, int height) {
        this.mFile = imageFile;
        this.X = X;
        this.Y = Y;
        this.width = width;
        this.height = height;
    }

}

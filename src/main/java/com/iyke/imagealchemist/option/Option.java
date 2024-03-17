package com.iyke.imagealchemist.option;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Option {
    private boolean useLink;
    private boolean setBuffer;
    private boolean grayscale;
    private int rotation;
    private String imageContentType;
    private String genImageContentType;

    public Option(boolean useLink, boolean setBuffer, boolean grayscale, int rotation, String imageContentType, String genImageContentType) {
        this.useLink = useLink;
        this.setBuffer = setBuffer;
        this.grayscale = grayscale;
        this.rotation = rotation;
        this.imageContentType = imageContentType;
        this.genImageContentType = genImageContentType;
    }

    public Option(boolean useLink, boolean grayscale, String imageContentType) {
        this.useLink = useLink;
        this.grayscale = grayscale;
        this.imageContentType = imageContentType;
    }
    
    public boolean isUseLink() {
        return useLink;
    }

    public void setUseLink(boolean useLink) {
        this.useLink = useLink;
    }

    public boolean isSetBuffer() {
        return setBuffer;
    }

    public void setSetBuffer(boolean setBuffer) {
        this.setBuffer = setBuffer;
    }

    public boolean isGrayscale() {
        return grayscale;
    }

    public void setGrayscale(boolean grayscale) {
        this.grayscale = grayscale;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getGenImageContentType() {
        return genImageContentType;
    }

    public void setGenImageContentType(String genImageContentType) {
        this.genImageContentType = genImageContentType;
    }

}

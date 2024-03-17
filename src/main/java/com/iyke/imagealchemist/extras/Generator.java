/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iyke.imagealchemist.extras;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author user
 */
public class Generator {
     public static String generateRandomString(int length) {
          String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // Use SecureRandom for secure random number generation
        SecureRandom random = new SecureRandom();

        StringBuilder randomString = new StringBuilder(length);

        // Generate random characters and append them to the StringBuilder
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }
       
         return randomString.toString();
     }
     
     public static ArrayList getFileResource(Path imagePath)
     {  
         var list=new ArrayList();
        try{
            Resource imageResource=new FileSystemResource(imagePath);
            list.add(imageResource);
            list.add(Files.probeContentType(imagePath));
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return list;
        }
     }
     
       public static BufferedImage image2GrayScale(BufferedImage image) throws IOException
    {
        int width=image.getWidth();
        int height=image.getHeight();
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                Color color=new Color(image.getRGB(i,j));
                int red=color.getRed();
                int green=color.getGreen();
                int blue=color.getBlue();
                int average=(int) (red+green+blue)/3;
                image.setRGB(i,j,new Color(average,average,average).getRGB());
            }
        }
        
        return image;
    }
       
       public static BufferedImage getCroppedImage(BufferedImage image,int x,int y,int w,int h)
       {
           BufferedImage croppedImage=image.getSubimage(x, y, w, h);
           return croppedImage;
       }
       
       public static BufferedImage convertToBufferedImage(Image image){
           BufferedImage newImage=new BufferedImage(image.getWidth(null),image.getHeight(null),
           BufferedImage.TYPE_INT_ARGB
           );
           Graphics2D g2d=newImage.createGraphics();
           g2d.drawImage(image, 0, 0, null);
           g2d.dispose();
           return newImage;
       }
}

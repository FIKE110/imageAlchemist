/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iyke.imagealchemist.service;

import com.iyke.imagealchemist.extras.Generator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author user
 */
@Service
public class ImageProcessService {
    private final String uploadPath="C:/projects/springboot/uploads/";
    
    public String saveImage(MultipartFile file) throws IOException
    {
        String filename=Generator.generateRandomString(17)+".png";
        Path path=Paths.get(uploadPath+filename);
        Files.write(path, file.getBytes());
        return filename;
    }
    
    public ArrayList getStoredImage(String imagename) throws IOException
    {
        //ArrayList list=new ArrayList();
        Path imagePath=Paths.get(uploadPath+imagename);
        var list=Generator.getFileResource(imagePath);
        return list;
    }
    
    public String renameImage(String name,String to) throws IOException
    {
        Path imagePath=Paths.get(uploadPath,name);
        Path newPath=Files.move(imagePath, Paths.get(uploadPath,to));
        return "file was successfully renamed to "+to;
    }
    
   public List getGrayScale(String imagePath) throws IOException
   {
       var image=ImageIO.read(Paths.get(uploadPath+imagePath).toFile());
       BufferedImage grayImage=Generator.image2GrayScale(image);
       var byteArray=new ByteArrayOutputStream();
       ImageIO.write(image, "png", byteArray);
       var list=List.of(byteArray.toByteArray());
       return list;
   }
   
   public List getCroppedImage(String dimension,String imagePath) throws IOException
   {
       Map<String,Integer> dimensions=new HashMap<>();
       var dimension_array=dimension.split("_");
       for(int i=0;i<dimension_array.length;i=i+2)
       {
           dimensions.put(dimension_array[i], Integer.parseInt(dimension_array[i+1]));
       }
       var image=ImageIO.read(Paths.get(uploadPath+imagePath).toFile());
       var croppedImage=Generator.
               getCroppedImage(image,
                       dimensions.get("x"), dimensions.get("y"),
                       dimensions.get("w")
                       , dimensions.get("h"));
       var byteArray=new ByteArrayOutputStream();
       ImageIO.write(croppedImage, "png", byteArray);
       var list=List.of(byteArray.toByteArray());
       return list;
   }
    
   public List getFileList(){
       File path=new File(uploadPath);
       File[] files=path.listFiles();
       ArrayList<String> fileList=new ArrayList<>();
       for(int i=0;i<files.length;i++){
           if(files[i].isFile()){
               fileList.add(files[i].getName());
           }
       }
       
       return fileList;
   }
   
    public String saveImage(BufferedImage data) throws IOException
    {
        ImageIO.write(data, "png", new File(uploadPath));
        return uploadPath;
    }
    
    
    public List resizeImage(String imagePath,String dimension) throws IOException
    {
         Map<String,Integer> dimensions=new HashMap<>();
       var dimension_array=dimension.split("_");
       for(int i=0;i<dimension_array.length;i=i+2)
       {
           dimensions.put(dimension_array[i], Integer.valueOf(dimension_array[i+1]));
       }
        var image= ImageIO.read(Paths.get(uploadPath+imagePath).toFile());
        Image scaledImage=image.getScaledInstance(
                dimensions.get("w"), dimensions.get("h"),Image.SCALE_DEFAULT);
        var byteArray=new ByteArrayOutputStream();
       ImageIO.write(Generator.convertToBufferedImage(scaledImage)
               , "png", byteArray);
        List list=List.of(byteArray.toByteArray());
       return list;
    }
    
      public List getImageByShape(String imagePath, String shape, String dimension) throws IOException {
          Map<String,Double> dimensions=new HashMap<>();
       var dimension_array=dimension.split("_");
       for(int i=0;i<dimension_array.length;i=i+2)
       {
           dimensions.put(dimension_array[i], Double.valueOf(dimension_array[i+1]));
       }
       BufferedImage newImage=null;
        var image=ImageIO.read(Paths.get(uploadPath+imagePath).toFile());
        if(shape.equalsIgnoreCase("triangl")){
            int[] keysX={dimensions.get("x1").intValue(),dimensions.get("x2").intValue(),dimensions.get("x3").intValue()};
            int[] keysY={dimensions.get("y1").intValue(),dimensions.get("y2").intValue(),dimensions.get("y3").intValue()};
            Arrays.sort(keysX);
            Arrays.sort(keysY);
            System.out.println(keysY[0]);
            System.out.println(keysY[2]);
            newImage = new BufferedImage(keysX[2]-keysX[0]
                ,keysY[2]-keysY[0]
                   , BufferedImage.TYPE_INT_RGB);
        }
        else{
        newImage = new BufferedImage(dimensions.get("w").intValue()
                ,dimensions.get("h").intValue(), BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D graphics = newImage.createGraphics();

        // Set background color
        graphics.setColor(new Color(0,0,0,0));
        graphics.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());

        // Choose shape and draw it
        switch (shape.toLowerCase()) {
            case "square" -> {
            }
            case "circle" -> drawCircle(graphics, image,dimensions);
            case "triangle" -> drawTriangle(graphics,image,dimensions);
            case "star" -> {
            }
            default -> throw new IllegalArgumentException("Invalid shape");
        }
        //drawSquare(graphics, dimension);
        //drawTriangle(graphics, dimension);
        //drawStar(graphics, dimension);

        // Save the image to a file
          var byteArray=new ByteArrayOutputStream();
        graphics.dispose();
         ImageIO.write(newImage
               , "png", byteArray);
        List list=List.of(byteArray.toByteArray());
        return list;
    }
      
      
       private void drawSquare(Graphics2D graphics, int dimension) {
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0, 0, dimension, dimension);
    }

    private void drawCircle(Graphics2D graphics, BufferedImage image,Map<String,Double> dimensions) {
      Ellipse2D.Double circle = new Ellipse2D.Double(0,0
              ,dimensions.get("w"),dimensions.get("h"));
      graphics.setClip(circle);
      graphics.drawImage(image, -dimensions.get("x").intValue(), -dimensions.get("y").intValue(), null);
    }

    private void drawTriangle(Graphics2D graphics, BufferedImage image,Map<String,Double> dimensions) {
        int x1=dimensions.get("x1").intValue();
        int x2=dimensions.get("x2").intValue();
        int x3=dimensions.get("x3").intValue();
        int y1=dimensions.get("y1").intValue();
        int y2=dimensions.get("y2").intValue();
        int y3=dimensions.get("y3").intValue();
             //int x1=dimensions.get("x1").intValue();
        int[] xPoints = {x1, x2, x3};  // x-coordinates of polygon vertices
        int[] yPoints = {y1, y2, y3};    // y-coordinates of polygon vertices
        int numPoints = xPoints.length;
        System.out.println("hello world");
        Polygon polygon = new Polygon(xPoints, yPoints, numPoints);
        graphics.setClip(polygon);
         graphics.drawImage(image, -dimensions.get("x").intValue(), -dimensions.get("y").intValue(), null);
    }

    private void drawStar(Graphics2D graphics, int dimension) {
        // Implement the star drawing logic
    }
}

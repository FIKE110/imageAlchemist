package com.iyke.imagealchemist.controllers;

import com.iyke.imagealchemist.service.ImageProcessService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path="/api/v1")
public class ImageAlchemistController {
    
    
    private final ImageProcessService imageProcessService;
    
    @Autowired
    public ImageAlchemistController(ImageProcessService imageProcessService)
    {
        this.imageProcessService=imageProcessService;
    }
  
    
    @GetMapping("/")
    public String hello()
    {
        return "hello world";
    }
   
    @GetMapping("/image")
    public ResponseEntity sendImage(@RequestParam("name") String name) throws IOException
    {
        ArrayList list=imageProcessService.getStoredImage(name);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf((String) list.get(1)))
                .body((Resource) list.get(0))
                ;
    }
    
    @GetMapping("images/list")
    public ResponseEntity listImages(){
        List fileList=imageProcessService.getFileList();
        return new ResponseEntity<>(fileList,HttpStatus.OK);
    }
    
    @GetMapping("image/rename")
    public ResponseEntity<String> renameImage(
            @RequestParam("name") String name,@RequestParam("to") String to)
            throws IOException
    {
        String newName=imageProcessService.renameImage(name, to);
       return  new ResponseEntity<>(newName,HttpStatus.OK);
    }
    
    @GetMapping("image/grayscale")
    public ResponseEntity toGrayScale(@RequestParam("name") String name) throws IOException
    {
        var imageList=imageProcessService.getGrayScale(name);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body((byte[]) imageList.get(0));
    }
    
    @GetMapping("image/crop")
    public ResponseEntity getCroppedImage(@RequestParam("name") String name,
            @RequestParam("dimension") String dimension) throws IOException
    {
        var imageList=imageProcessService.getCroppedImage(dimension, name);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body((byte[]) imageList.get(0));
    }
    
    @GetMapping("image/resize")
    public ResponseEntity getResizedImage(@RequestParam("name") String name,
            @RequestParam("dimension") String dimension
            ) throws IOException
    {
        var imageList=imageProcessService.resizeImage(name, dimension);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body((byte[]) imageList.get(0));
    }
    
    @GetMapping("image/shape")
    public ResponseEntity getImageWithShape(
            @RequestParam("name") String name,
            @RequestParam("shape") String shape,
            @RequestParam("dimension") String dimension) throws IOException
            {
        var imageList=imageProcessService.getImageByShape(name,shape,dimension);
         return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body((byte[]) imageList.get(0));
    }
    
    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) throws IOException
    {
        String imagePath=imageProcessService.saveImage(file);
        return new ResponseEntity<>(imagePath,HttpStatus.OK);
    }
   
}
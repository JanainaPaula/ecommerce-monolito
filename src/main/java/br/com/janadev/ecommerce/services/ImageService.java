package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile){
        String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!"png".equals(extension) && !"jpg".equals(extension)){
            throw new FileException("Somente imagens PNG e JPG são permitidas.");
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(uploadedFile.getInputStream());
            if ("png".equals(extension)){
                bufferedImage = pngToJpg(bufferedImage);
            }
            return bufferedImage;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo.");
        }
    }

    public BufferedImage pngToJpg(BufferedImage bufferedImage) {
        BufferedImage jpgImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage image, String extention){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, extention, outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    public BufferedImage cropSquare(BufferedImage image){
        int min = Math.min(image.getHeight(), image.getWidth());
        return Scalr.crop(image,
                (image.getWidth()/2) - (min/2),
                (image.getHeight()/2) - (min/2),
                min,
                min);
    }

    public BufferedImage resize(BufferedImage image, int size){
        return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, size);
    }
}

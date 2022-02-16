package com.proteccion.technical.test.TechnicalTest.services;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proteccion.technical.test.TechnicalTest.requests.UploadFileRequest;

@Service
public class AppService {

	@Value("${app.width.max}")
	private int widthMax;

	@Value("${app.height.max}")
	private int heigthMax;

	public void saveFile(UploadFileRequest request) throws IOException {
		MultipartFile multipartFile = request.getMultipartFile();
		Files.copy(multipartFile.getInputStream(), Paths.get("uploads").resolve(request.getNameFile()));
		File imageFile = new File("uploads" + File.separator + request.getNameFile());
		Image image = ImageIO.read(imageFile);
		int height = image.getHeight(null);
		int width = image.getWidth(null);
		if (width > widthMax || height > heigthMax) {
			Dimension imgSize = new Dimension(500, 100);
			Dimension targetSize = new Dimension(widthMax, heigthMax);
			Dimension newSize = getScaledDimension(imgSize, targetSize);
			BufferedImage bufferedImage = toBufferedImage(image);
			BufferedImage processedImage = resizeImage(bufferedImage, ((Double) newSize.getWidth()).intValue(),
					((Double) newSize.getHeight()).intValue());
			File newFile = new File("uploads" + File.separator + "processed" + request.getNameFile());
			ImageIO.write(processedImage, "jpg", newFile);

		}
		System.out.println("height: " + height + " width: " + width);
	}

	public Dimension getScaledDimension(Dimension imgSize, Dimension targetSize) {
		int originalWidth = imgSize.width;
		int originalHeight = imgSize.height;
		int boundWidth = targetSize.width;
		int boundHeight = targetSize.height;
		int newWidth = originalWidth;
		int newHeight = originalHeight;
		if (originalWidth > boundWidth) {
			newWidth = boundWidth;
			newHeight = (newWidth * originalHeight) / originalWidth;
		}
		if (newHeight > boundHeight) {
			newHeight = boundHeight;
			newWidth = (newHeight * originalWidth) / originalHeight;
		}
		return new Dimension(newWidth, newHeight);
	}

	private BufferedImage resizeImage(BufferedImage originalImage, Integer img_width, Integer img_height) {
		BufferedImage resizedImage = new BufferedImage(img_width, img_height, originalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, img_width, img_height, null);
		g.dispose();
		return resizedImage;
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		// Return the buffered image
		return bimage;
	}

}

package com.eclipsercp.swtjface.services;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;

/**
 * A service for creating new image descriptor scaled to 32x32 size for toolbars and menus.
 */
public class ImageDescriptorService {
	
	private static ImageDescriptorService instance;
	
	/**
	 * Constructs a new instance of this class or return existing instance if it have been already instantiated.
	 */
	public static synchronized ImageDescriptorService getInstance() {
		if (instance == null) {
			instance = new ImageDescriptorService();
		}
		return instance;
	}
	
	/**
	 * Constructs and returns a new image descriptor.
	 * 
	 * @param filename
	 *            the filename of the file to create the image descriptor from
	 */
	public ImageDescriptor getImageDescriptor(String filename) {
		ImageDescriptor id = ImageDescriptor.createFromFile(null, filename);
		ImageData imgData = id.getImageData();
		imgData = imgData.scaledTo(32, 32);
		return ImageDescriptor.createFromImageData(imgData);
	}

}

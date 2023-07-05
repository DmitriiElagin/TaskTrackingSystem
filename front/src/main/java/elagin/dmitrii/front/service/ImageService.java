package elagin.dmitrii.front.service;

import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Dmitrii Elagin
 * Date 02.06.2023 12:01
 */
public class ImageService {
  public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
    return inputStream.readAllBytes();
  }

  public static StreamResource byteArrayToStreamResource(byte[] data) {
    return new StreamResource("image", () -> new ByteArrayInputStream(data));
  }
}
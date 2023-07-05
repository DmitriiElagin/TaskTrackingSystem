package elagin.dmitrii.front.service;

import com.vaadin.flow.server.StreamResource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dmitrii Elagin
 * Date 02.06.2023 12:27
 */
class ImageServiceTest {

  @Test
  void inputStreamToByteArray() {
    try (InputStream in = getInputStream("/icon.png")) {
      byte[] data = ImageService.inputStreamToByteArray(in);
      assertNotNull(data);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  void byteArrayToStreamResource() {
    try (InputStream in = getInputStream("/icon.png")) {
      StreamResource resource = ImageService.byteArrayToStreamResource(in.readAllBytes());
      assertNotNull(resource);
      assertEquals("image", resource.getName());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  InputStream getInputStream(String resource) {
    InputStream in;
    in = ImageServiceTest.class.getResourceAsStream(resource);

    if (in == null) {
      fail("in is null!");
    }

    return in;
  }
}
package com.example.flowablebpmntoimage.controller;

import com.example.flowablebpmntoimage.service.ModelParserService;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ModelParserController {

  final ModelParserService modelParserService;

  public ModelParserController(ModelParserService modelParserService) {
    this.modelParserService = modelParserService;
  }

  @PostMapping("/bpmn")
  public void parseBpmnToImage(@RequestParam("file") MultipartFile file,
      HttpServletResponse response) throws IOException, XMLStreamException {
    InputStream imageStream = modelParserService.convertBpmnToImage(file.getInputStream());
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    IOUtils.copy(imageStream, response.getOutputStream());
  }
}

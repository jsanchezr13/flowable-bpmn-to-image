package com.example.flowablebpmntoimage.service;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;

public interface ModelParserService {

  InputStream convertBpmnToImage(InputStream modelStream) throws XMLStreamException;
}

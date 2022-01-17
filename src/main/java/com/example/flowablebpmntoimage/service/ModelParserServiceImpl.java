package com.example.flowablebpmntoimage.service;

import com.example.flowablebpmntoimage.util.XmlUtil;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.springframework.stereotype.Service;

@Service
public class ModelParserServiceImpl implements ModelParserService {

  private final ProcessEngine processEngine;

  public ModelParserServiceImpl(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  @Override
  public InputStream convertBpmnToImage(InputStream modelStream) throws XMLStreamException {
    ProcessEngineConfiguration processEngineConfiguration = processEngine
        .getProcessEngineConfiguration();
    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
    InputStreamReader xmlIn = new InputStreamReader(modelStream, StandardCharsets.UTF_8);
    XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
    BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xtr);

    return processEngineConfiguration.getProcessDiagramGenerator().generateDiagram(bpmnModel, "png",
        processEngineConfiguration.getActivityFontName(),
        processEngineConfiguration.getLabelFontName(),
        processEngineConfiguration.getAnnotationFontName(),
        processEngineConfiguration.getClassLoader(),
        true);
  }
}

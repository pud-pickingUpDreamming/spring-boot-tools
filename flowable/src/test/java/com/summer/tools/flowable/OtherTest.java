package com.summer.tools.flowable;

import org.flowable.common.engine.impl.util.ReflectUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

@SpringBootTest
public class OtherTest {
    @Test
    public void SpringGetResourceTest() throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource("processes/expense.bpmn20.xml");
        final File file = classPathResource.getFile();

        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    public void FlowableGetResourceTest() throws IOException {
        InputStream inputStream = ReflectUtil.getResourceAsStream("processes/expense.bpmn20.xml");
        System.out.println(inputStream.available());
        byte[] bpmnBytes = new byte[inputStream.available()];
        int length = inputStream.read(bpmnBytes);
        String XmlData = new String(bpmnBytes);
        System.out.println(inputStream.available());
        System.out.println(length);
        System.out.println(XmlData);
    }
}

package com.summer.tools.flowable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author john.wang
 */
@Slf4j
public abstract class AbstractControllerTest {

    @Resource
    private MockMvc mockMvc;

    protected void get(String url, Map<String, String> map) throws Exception {
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(setParams(map))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        log.info("测试get请求，地址：{},结果：{}", url, result.getResponse().getContentAsString());
    }

    protected void delete(String url, Map<String, String> map) throws Exception {
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .delete(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(setParams(map))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        log.info("测试delete请求，地址：{},结果：{}", url, result.getResponse().getContentAsString());
    }

    protected void post(String url, Map<String, String> map) throws Exception {
        String result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .params(setParams(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("测试post请求，地址：{},结果：{}", url, result);
    }

    protected void put(String url, Map<String, String> map) throws Exception {
        String result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(url)
                        .params(setParams(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("测试put请求，地址：{},结果：{}", url, result);
    }


    protected void postJson(String url, String content) throws Exception {
        String result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("测试postJson格式请求，地址：{},结果：{}", url, result);
    }

    protected void putJson(String url, String content) throws Exception {
        String result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(url)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("测试putJson格式请求，地址：{},结果：{}", url, result);
    }

    protected void uploadFile(String url, File file) throws Exception {
        if (!file.exists()) {
            throw new NullPointerException("文件不存在，路径：" + file.getAbsolutePath());
        }
        InputStream stream = new FileInputStream(file);
        String result = mockMvc.perform(
                multipart(url)
                    .file(new MockMultipartFile("file", file.getName(),MediaType.MULTIPART_FORM_DATA_VALUE, stream)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
        log.info("测试上传文件请求，地址：{},结果：{}", url, result);
    }

    private MultiValueMap<String, String> setParams(Map<String, String> map) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(map);
        return params;
    }
}

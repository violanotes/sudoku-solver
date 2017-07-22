package com.violanotes.sudokusolver;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by pc on 7/20/2017.
 */
public class TestUtils {
    public static String getJson(String path) throws Exception {
        Resource resource = new ClassPathResource(path);
        return IOUtils.toString((resource).getURL());
    }

}

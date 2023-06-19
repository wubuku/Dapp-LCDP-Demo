package org.dddml.roochdemocontracts.restful.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class RoochCliCheatsheetConfigurer implements WebMvcConfigurer {

    @Value("${rooch.contract.address}")
    private String contractAddress;

    private Map<String, String> getRoochCLICheatsheetReplacements() {
        Map<String, String> map = new HashMap<>();
        map.put("_CONTRACT_ADDRESS_", contractAddress);
        return map;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/rooch.contract/**")
                .addResourceLocations("classpath:/rooch/contract/")
                //.setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        if (resourcePath.endsWith("RoochMoveCLICheatsheet.md")) {
                            Resource tplResource = location.createRelative(resourcePath);
                            String tplContent = StreamUtils.copyToString(tplResource.getInputStream(), StandardCharsets.UTF_8);

                            String content = tplContent;
                            Map<String, String> replacements = getRoochCLICheatsheetReplacements();
                            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                                content = content.replace(entry.getKey(), entry.getValue());
                            }

                            File tempFile = File.createTempFile("temp", ".md");
                            FileWriter writer = new FileWriter(tempFile);
                            writer.write(content);
                            writer.close();

                            // Return a resource with a physical location.
                            return new FileSystemResource(tempFile);
                        } else {
                            return super.getResource(resourcePath, location);
                        }
                    }
                });
    }
}


package org.dddml.suidemocontracts.restful.config;

import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.repository.MoveObjectIdGeneratorObjectRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.dddml.suidemocontracts.restful.config.SuiCliCheatsheetConstants.ID_GENERATOR_OBJECT_NAME_TO_ARGUMENT_HINT_MAP;
import static org.dddml.suidemocontracts.restful.config.SuiCliCheatsheetConstants.PACKAGE_ID_ARGUMENT_HINT;

@Configuration
public class SuiCliCheatsheetConfigurer implements WebMvcConfigurer {

    @Autowired
    private MoveObjectIdGeneratorObjectRepository moveObjectIdGeneratorObjectRepository;
    @Autowired
    private SuiPackageRepository suiPackageRepository;

    private Map<String, String> getSuiClientCLICheatsheetReplacements() {
        Map<String, String> map = new HashMap<>();
        moveObjectIdGeneratorObjectRepository.findAll().forEach(moveObjectIdGeneratorObject -> {
            String cliArgumentHint = ID_GENERATOR_OBJECT_NAME_TO_ARGUMENT_HINT_MAP.get(moveObjectIdGeneratorObject.getName());
            if (cliArgumentHint != null) {
                map.put(cliArgumentHint, moveObjectIdGeneratorObject.getObjectId());
            }
        });

        suiPackageRepository.findById(ContractConstants.DEFAULT_SUI_PACKAGE_NAME)
                .ifPresent(suiPackage -> {
                    map.put(PACKAGE_ID_ARGUMENT_HINT, suiPackage.getObjectId());
                });
        return map;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/sui.contract/**")
                .addResourceLocations("classpath:/sui/contract/")
                //.setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        if (resourcePath.endsWith("SuiClientCLICheatsheet.md")) {
                            Resource tplResource = location.createRelative(resourcePath);
                            String tplContent = StreamUtils.copyToString(tplResource.getInputStream(), StandardCharsets.UTF_8);

                            String content = tplContent;
                            Map<String, String> replacements = getSuiClientCLICheatsheetReplacements();
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


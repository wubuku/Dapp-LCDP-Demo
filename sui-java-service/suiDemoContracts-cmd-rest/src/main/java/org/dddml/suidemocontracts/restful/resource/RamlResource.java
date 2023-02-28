package org.dddml.suidemocontracts.restful.resource;

import com.syj.raml.model.api.Raml;
import com.syj.raml.parser.RamlParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by yangjiefeng on 2018/8/8.
 */
@RestController
public class RamlResource {

    @GetMapping(path = "api.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Raml getRamlJson() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                //"dddml-wms-restful.raml"
                "suiDemoContracts-cmd-rest.raml"
        );
        return RamlParser.parse(in);
    }

}

package org.dddml.suidemocontracts.sui.contract.config;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class SuiJsonRpcConfig {
    @Value("${sui.contract.jsonrpc.url}")
    private String jsonRpcUrl;

    @Bean
    public SuiJsonRpcClient suiJsonRpcClient() throws MalformedURLException {
        return new SuiJsonRpcClient(jsonRpcUrl);
    }
}

package org.dddml.roochdemocontracts.rooch.contract.config;

import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class RoochJsonRpcConfig {
    @Value("${rooch.contract.jsonrpc.url}")
    private String jsonRpcUrl;

    @Bean
    public RoochJsonRpcClient roochJsonRpcClient() throws MalformedURLException {
        return new RoochJsonRpcClient(jsonRpcUrl);
    }
}

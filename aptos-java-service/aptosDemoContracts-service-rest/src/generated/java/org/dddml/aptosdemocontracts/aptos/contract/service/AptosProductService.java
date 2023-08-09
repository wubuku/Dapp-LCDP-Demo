// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.service;

import com.github.wubuku.aptos.utils.NodeApiClient;
import org.dddml.aptosdemocontracts.domain.*;
import org.dddml.aptosdemocontracts.domain.product.*;
import org.dddml.aptosdemocontracts.aptos.contract.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.*;
import java.util.*;
import java.math.*;

@Service
public class AptosProductService {

    @Autowired
    private ProductStateRepository productStateRepository;


    private AptosProductStateRetriever aptosProductStateRetriever;

    @Autowired
    public AptosProductService(
        @Value("${aptos.contract.address}")
        String aptosContractAddress,
        NodeApiClient aptosNodeApiClient,
        AptosAccountRepository aptosAccountRepository
    ) {
        this.aptosProductStateRetriever = new AptosProductStateRetriever(
                aptosNodeApiClient,
                aptosContractAddress,
                aptosAccountRepository,
                productId -> {
                    ProductState.MutableProductState s = new AbstractProductState.SimpleProductState();
                    s.setProductId(productId);
                    return s;
                }
        );
    }

    @Transactional
    public void updateProductState(String productId) {
        ProductState productState = aptosProductStateRetriever.retrieveProductState(productId);
        if (productState == null) {
            return;
        }
        productStateRepository.merge(productState);
    }

    @Transactional
    public void deleteProduct(String productId) {
        ProductState.MutableProductState s = (ProductState.MutableProductState) productStateRepository.get(productId, true);
        if (s != null) {
            s.setDeleted(true);
            productStateRepository.merge(s);
        }
    }

}


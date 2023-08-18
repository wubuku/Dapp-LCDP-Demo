// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.domain.product.*;
import org.dddml.roochdemocontracts.rooch.contract.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.*;
import java.util.*;
import java.math.*;

@Service
public class RoochProductService {

    @Autowired
    private ProductStateRepository productStateRepository;


    private RoochProductStateRetriever roochProductStateRetriever;

    @Autowired
    public RoochProductService(RoochJsonRpcClient roochJsonRpcClient) {
        this.roochProductStateRetriever = new RoochProductStateRetriever(roochJsonRpcClient,
                productId -> {
                    ProductState.MutableProductState s = new AbstractProductState.SimpleProductState();
                    s.setProductId(productId);
                    return s;
                }
        );
    }

    @Transactional
    public void updateProductState(String objectId) {
        ProductState productState = roochProductStateRetriever.retrieveProductState(objectId);
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


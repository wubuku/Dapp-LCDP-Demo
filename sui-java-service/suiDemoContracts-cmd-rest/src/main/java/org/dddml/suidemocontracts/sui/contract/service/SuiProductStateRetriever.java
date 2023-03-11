// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.*;
import com.github.wubuku.sui.utils.*;
import org.dddml.suidemocontracts.domain.product.*;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.Product;

import java.util.*;
import java.math.*;
import java.util.function.*;

public class SuiProductStateRetriever {

    private SuiJsonRpcClient suiJsonRpcClient;

    private Function<String, ProductState.MutableProductState> productStateFactory;

    public SuiProductStateRetriever(SuiJsonRpcClient suiJsonRpcClient,
                                  Function<String, ProductState.MutableProductState> productStateFactory
    ) {
        this.suiJsonRpcClient = suiJsonRpcClient;
        this.productStateFactory = productStateFactory;
    }

    public ProductState retrieveProductState(String objectId) {
        GetMoveObjectDataResponse<Product> getObjectDataResponse = suiJsonRpcClient.getMoveObject(
                objectId, Product.class
        );

        Product product = getObjectDataResponse.getDetails().getData().getFields();
        return toProductState(product);
    }

    private ProductState toProductState(Product product) {
        ProductState.MutableProductState productState = productStateFactory.apply(product.getProductId());
        productState.setId_(product.getId().getId());
        productState.setVersion(product.getVersion());
        productState.setName(product.getName());
        productState.setUnitPrice(product.getUnitPrice());
        return productState;
    }

    
}


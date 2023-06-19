// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.bean.GetAnnotatedStatesResponseMoveStructItem;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.domain.product.*;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.rooch.contract.DomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.bcs.BcsDomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.contract.Product;

import java.util.*;
import java.math.*;
import java.util.function.*;

public class RoochProductStateRetriever {

    private RoochJsonRpcClient roochJsonRpcClient;

    private Function<String, ProductState.MutableProductState> productStateFactory;


    public RoochProductStateRetriever(RoochJsonRpcClient roochJsonRpcClient,
                                    Function<String, ProductState.MutableProductState> productStateFactory
    ) {
        this.roochJsonRpcClient = roochJsonRpcClient;
        this.productStateFactory = productStateFactory;
    }

    public ProductState retrieveProductState(String objectId) {
        List<GetAnnotatedStatesResponseMoveStructItem<Product.MoveObject>> getObjectListResponse = roochJsonRpcClient.getMoveStructAnnotatedStates(
                "/object/" + com.github.wubuku.rooch.utils.HexUtils.formatHex(objectId),
                Product.MoveObject.class
        );
        if (getObjectListResponse.size() == 0) {
            return null;
        }
        Product.MoveObject product = getObjectListResponse.get(0).getMoveValue().getValue();
        return toProductState(product);
    }

    private ProductState toProductState(Product.MoveObject productObj) {
        Product product = productObj.getValue().getValue();
        ProductState.MutableProductState productState = productStateFactory.apply(product.getProductId());
        productState.setId_(productObj.getId());
        productState.setVersion(product.getVersion());
        productState.setName(product.getName());
        productState.setUnitPrice(product.getUnitPrice());
        return productState;
    }

}


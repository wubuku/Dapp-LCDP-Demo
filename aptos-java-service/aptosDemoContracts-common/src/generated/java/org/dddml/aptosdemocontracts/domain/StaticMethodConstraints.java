// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain;

import org.dddml.aptosdemocontracts.specialization.ReflectUtils;
import org.dddml.aptosdemocontracts.specialization.MutationContext;
import org.dddml.aptosdemocontracts.specialization.VerificationContext;
import org.dddml.aptosdemocontracts.domain.order.*;
import java.math.BigInteger;
import org.dddml.aptosdemocontracts.domain.*;
import java.util.Date;
import org.dddml.aptosdemocontracts.domain.product.*;
import org.dddml.aptosdemocontracts.domain.daysummary.*;

public class StaticMethodConstraints {

    public static void assertStaticVerificationAndMutationMethods() {

        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.CreateLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "productId", "quantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveItemLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, VerificationContext.class},
                    new String[]{"_", "productId"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateItemQuantityLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "productId", "quantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateEstimatedShipDateLogic",
                    "verify",
                    new Class[]{OrderState.class, Day.class, VerificationContext.class},
                    new String[]{"_", "estimatedShipDate"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.AddOrderShipGroupLogic",
                    "verify",
                    new Class[]{OrderState.class, Integer.class, String.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "shipGroupSeqId", "shipmentMethod", "productId", "quantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.CancelOrderShipGroupQuantityLogic",
                    "verify",
                    new Class[]{OrderState.class, Integer.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "shipGroupSeqId", "productId", "cancelQuantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupItemLogic",
                    "verify",
                    new Class[]{OrderState.class, Integer.class, String.class, VerificationContext.class},
                    new String[]{"_", "shipGroupSeqId", "productId"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupLogic",
                    "verify",
                    new Class[]{OrderState.class, Integer.class, VerificationContext.class},
                    new String[]{"_", "shipGroupSeqId"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.product.CreateLogic",
                    "verify",
                    new Class[]{ProductState.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "name", "unitPrice"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.product.UpdateLogic",
                    "verify",
                    new Class[]{ProductState.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "name", "unitPrice"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.product.DeleteLogic",
                    "verify",
                    new Class[]{ProductState.class, VerificationContext.class},
                    new String[]{"_"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.daysummary.CreateLogic",
                    "verify",
                    new Class[]{DaySummaryState.class, String.class, int[].class, String[].class, String.class, Integer[].class, Long[].class, BigInteger[].class, BigInteger[].class, BigInteger[].class, VerificationContext.class},
                    new String[]{"_", "description", "metaData", "arrayData", "optionalData", "u16ArrayData", "u32ArrayData", "u64ArrayData", "u128ArrayData", "u256ArrayData"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.CreateLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "productId", "quantity", "unitPrice", "totalAmount", "owner", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveItemLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "productId", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateItemQuantityLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "productId", "quantity", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateEstimatedShipDateLogic",
                    "mutate",
                    new Class[]{OrderState.class, Day.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "estimatedShipDate", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.AddOrderShipGroupLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, String.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "shipGroupSeqId", "shipmentMethod", "productId", "quantity", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.CancelOrderShipGroupQuantityLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "shipGroupSeqId", "productId", "cancelQuantity", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupItemLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, String.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "shipGroupSeqId", "productId", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "shipGroupSeqId", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.product.CreateLogic",
                    "mutate",
                    new Class[]{ProductState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "name", "unitPrice", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.product.UpdateLogic",
                    "mutate",
                    new Class[]{ProductState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "name", "unitPrice", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.product.DeleteLogic",
                    "mutate",
                    new Class[]{ProductState.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.aptosdemocontracts.domain.daysummary.CreateLogic",
                    "mutate",
                    new Class[]{DaySummaryState.class, String.class, int[].class, String[].class, String.class, Integer[].class, Long[].class, BigInteger[].class, BigInteger[].class, BigInteger[].class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new String[]{"_", "description", "metaData", "arrayData", "optionalData", "u16ArrayData", "u32ArrayData", "u64ArrayData", "u128ArrayData", "u256ArrayData", "aptosEventVersion", "aptosEventSequenceNumber", "aptosEventType", "aptosEventGuid", "status"}
            );



    }

}



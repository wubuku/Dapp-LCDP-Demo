package org.dddml.suidemocontracts.domain;

import org.dddml.suidemocontracts.specialization.ReflectUtils;
import org.dddml.suidemocontracts.specialization.MutationContext;
import org.dddml.suidemocontracts.specialization.VerificationContext;
import org.dddml.suidemocontracts.domain.domainname.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.order.*;
import org.dddml.suidemocontracts.domain.product.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.domain.daysummary.*;

public class StaticMethodConstraints {

    public static void assertStaticVerificationAndMutationMethods() {

        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.domainname.RegisterLogic",
                    "verify",
                    new Class[]{DomainNameState.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "registrationPeriod"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.domainname.RenewLogic",
                    "verify",
                    new Class[]{DomainNameState.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "renewPeriod"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.order.CreateLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "product", "quantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.order.RemoveItemLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, VerificationContext.class},
                    new String[]{"_", "productId"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.order.UpdateItemQuantityLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "productId", "quantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.product.CreateLogic",
                    "verify",
                    new Class[]{ProductState.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "name", "unitPrice"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.CreateLogic",
                    "verify",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "product", "quantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.RemoveItemLogic",
                    "verify",
                    new Class[]{OrderV2State.class, String.class, VerificationContext.class},
                    new String[]{"_", "productId"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateItemQuantityLogic",
                    "verify",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, VerificationContext.class},
                    new String[]{"_", "productId", "quantity"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateEstimatedShipDateLogic",
                    "verify",
                    new Class[]{OrderV2State.class, Day.class, VerificationContext.class},
                    new String[]{"_", "estimatedShipDate"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.daysummary.CreateLogic",
                    "verify",
                    new Class[]{DaySummaryState.class, String.class, int[].class, String[].class, int[].class, VerificationContext.class},
                    new String[]{"_", "description", "metaData", "arrayData", "optionalData"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.domainname.RegisterLogic",
                    "mutate",
                    new Class[]{DomainNameState.class, BigInteger.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "registrationPeriod", "owner", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.domainname.RenewLogic",
                    "mutate",
                    new Class[]{DomainNameState.class, BigInteger.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "renewPeriod", "account", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.order.CreateLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "product", "quantity", "unitPrice", "totalAmount", "owner", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.order.RemoveItemLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "productId", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.order.UpdateItemQuantityLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "productId", "quantity", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.product.CreateLogic",
                    "mutate",
                    new Class[]{ProductState.class, String.class, BigInteger.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "name", "unitPrice", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.CreateLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "product", "quantity", "unitPrice", "totalAmount", "owner", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.RemoveItemLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "productId", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateItemQuantityLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "productId", "quantity", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateEstimatedShipDateLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, Day.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "estimatedShipDate", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );


        ReflectUtils.assertStaticMethodIfClassExists(
                    "org.dddml.suidemocontracts.domain.daysummary.CreateLogic",
                    "mutate",
                    new Class[]{DaySummaryState.class, String.class, int[].class, String[].class, int[].class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new String[]{"_", "description", "metaData", "arrayData", "optionalData", "suiTimestamp", "suiTxDigest", "suiEventSeq", "suiPackageId", "suiTransactionModule", "suiSender", "suiType", "status"}
            );



    }

}



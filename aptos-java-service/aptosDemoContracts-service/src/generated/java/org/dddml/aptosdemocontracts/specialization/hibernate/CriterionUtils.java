package org.dddml.aptosdemocontracts.specialization.hibernate;

import org.dddml.support.criterion.*;

/**
 * Created by Yang on 2016/7/29.
 */
public class CriterionUtils {
    private CriterionUtils() {
    }

    public static org.hibernate.criterion.Criterion toHibernateCriterion(org.dddml.support.criterion.Criterion criterion) {

        org.hibernate.criterion.Criterion cr = null;

        if (criterion instanceof SimpleExpression) {
            SimpleExpression e = (SimpleExpression) (criterion);

            if (e.getOp().trim().equalsIgnoreCase(Restrictions.OP_EQ)) {
                cr = org.hibernate.criterion.Restrictions.eq(e.getPropertyName(), e.getValue());
            }
            if (e.getOp().trim().equalsIgnoreCase(Restrictions.OP_GT)) {
                cr = org.hibernate.criterion.Restrictions.gt(e.getPropertyName(), e.getValue());
            }
            if (e.getOp().trim().equalsIgnoreCase(Restrictions.OP_LT)) {
                cr = org.hibernate.criterion.Restrictions.lt(e.getPropertyName(), e.getValue());
            }
            if (e.getOp().trim().equalsIgnoreCase(Restrictions.OP_GE)) {
                cr = org.hibernate.criterion.Restrictions.ge(e.getPropertyName(), e.getValue());
            }
            if (e.getOp().trim().equalsIgnoreCase(Restrictions.OP_LE)) {
                cr = org.hibernate.criterion.Restrictions.le(e.getPropertyName(), e.getValue());
            }
            if (e.getOp().trim().equalsIgnoreCase(Restrictions.OP_LIKE)) {
                cr = org.hibernate.criterion.Restrictions.like(e.getPropertyName(), e.getValue());
            }
        } else {
            if (criterion instanceof InsensitiveLikeExpression) {
                InsensitiveLikeExpression e = (InsensitiveLikeExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.ilike(e.getPropertyName(), e.getValue());
            }
            if (criterion instanceof InExpression) {
                InExpression e = (InExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.in(e.getPropertyName(), e.getValues());
            }
            if (criterion instanceof NullExpression) {
                NullExpression e = (NullExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.isNull(e.getPropertyName());
            }
            if (criterion instanceof NotNullExpression) {
                NotNullExpression e = (NotNullExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.isNotNull(e.getPropertyName());
            }
            if (criterion instanceof BetweenExpression) {
                BetweenExpression e = (BetweenExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.between(e.getPropertyName(), e.getLo(), e.getHi());
            }
            if (criterion instanceof AndExpression) {
                AndExpression e = (AndExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.and(toHibernateCriterion(e.getLeftHandSide()), toHibernateCriterion(e.getRightHandSide()));
            }
            if (criterion instanceof OrExpression) {
                OrExpression e = (OrExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.or(toHibernateCriterion(e.getLeftHandSide()), toHibernateCriterion(e.getRightHandSide()));
            }
            if (criterion instanceof NotExpression) {
                NotExpression e = (NotExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.not(toHibernateCriterion(e.getCriterion()));
            }
            if (criterion instanceof Disjunction) {
                Disjunction e = (Disjunction) (criterion);
                org.hibernate.criterion.Disjunction j = org.hibernate.criterion.Restrictions.disjunction();
                for (Criterion c : e.getCriteria()) {
                    j.add(toHibernateCriterion(c));
                }
                cr = j;
            }
            if (criterion instanceof Conjunction) {
                Conjunction e = (Conjunction) (criterion);
                org.hibernate.criterion.Conjunction j = org.hibernate.criterion.Restrictions.conjunction();
                for (Criterion c : e.getCriteria()) {
                    j.add(toHibernateCriterion(c));
                }
                cr = j;
            }

            if (criterion instanceof EqPropertyExpression) {
                EqPropertyExpression e = (EqPropertyExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.eqProperty(e.getLhsPropertyName(), e.getRhsPropertyName());
            }
            if (criterion instanceof GtPropertyExpression) {
                GtPropertyExpression e = (GtPropertyExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.gtProperty(e.getLhsPropertyName(), e.getRhsPropertyName());
            }
            if (criterion instanceof LtPropertyExpression) {
                LtPropertyExpression e = (LtPropertyExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.ltProperty(e.getLhsPropertyName(), e.getRhsPropertyName());
            }
            if (criterion instanceof GePropertyExpression) {
                GePropertyExpression e = (GePropertyExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.geProperty(e.getLhsPropertyName(), e.getRhsPropertyName());
            }
            if (criterion instanceof LePropertyExpression) {
                LePropertyExpression e = (LePropertyExpression) (criterion);
                cr = org.hibernate.criterion.Restrictions.leProperty(e.getLhsPropertyName(), e.getRhsPropertyName());
            }
        }
        if (cr == null) {
            throw new UnsupportedOperationException(String.format("Not supported criterion. type name: %1$s, %2$s", criterion.getClass().getName(), criterion));
        }
        return cr;
    }


}

package com.hht.entity;

import java.util.ArrayList;
import java.util.List;

public class MsgRepExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsgRepExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMessageidIsNull() {
            addCriterion("messageid is null");
            return (Criteria) this;
        }

        public Criteria andMessageidIsNotNull() {
            addCriterion("messageid is not null");
            return (Criteria) this;
        }

        public Criteria andMessageidEqualTo(Integer value) {
            addCriterion("messageid =", value, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidNotEqualTo(Integer value) {
            addCriterion("messageid <>", value, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidGreaterThan(Integer value) {
            addCriterion("messageid >", value, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidGreaterThanOrEqualTo(Integer value) {
            addCriterion("messageid >=", value, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidLessThan(Integer value) {
            addCriterion("messageid <", value, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidLessThanOrEqualTo(Integer value) {
            addCriterion("messageid <=", value, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidIn(List<Integer> values) {
            addCriterion("messageid in", values, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidNotIn(List<Integer> values) {
            addCriterion("messageid not in", values, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidBetween(Integer value1, Integer value2) {
            addCriterion("messageid between", value1, value2, "messageid");
            return (Criteria) this;
        }

        public Criteria andMessageidNotBetween(Integer value1, Integer value2) {
            addCriterion("messageid not between", value1, value2, "messageid");
            return (Criteria) this;
        }

        public Criteria andTopnameIsNull() {
            addCriterion("topname is null");
            return (Criteria) this;
        }

        public Criteria andTopnameIsNotNull() {
            addCriterion("topname is not null");
            return (Criteria) this;
        }

        public Criteria andTopnameEqualTo(String value) {
            addCriterion("topname =", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameNotEqualTo(String value) {
            addCriterion("topname <>", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameGreaterThan(String value) {
            addCriterion("topname >", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameGreaterThanOrEqualTo(String value) {
            addCriterion("topname >=", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameLessThan(String value) {
            addCriterion("topname <", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameLessThanOrEqualTo(String value) {
            addCriterion("topname <=", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameLike(String value) {
            addCriterion("topname like", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameNotLike(String value) {
            addCriterion("topname not like", value, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameIn(List<String> values) {
            addCriterion("topname in", values, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameNotIn(List<String> values) {
            addCriterion("topname not in", values, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameBetween(String value1, String value2) {
            addCriterion("topname between", value1, value2, "topname");
            return (Criteria) this;
        }

        public Criteria andTopnameNotBetween(String value1, String value2) {
            addCriterion("topname not between", value1, value2, "topname");
            return (Criteria) this;
        }

        public Criteria andSendidenIsNull() {
            addCriterion("sendiden is null");
            return (Criteria) this;
        }

        public Criteria andSendidenIsNotNull() {
            addCriterion("sendiden is not null");
            return (Criteria) this;
        }

        public Criteria andSendidenEqualTo(String value) {
            addCriterion("sendiden =", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenNotEqualTo(String value) {
            addCriterion("sendiden <>", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenGreaterThan(String value) {
            addCriterion("sendiden >", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenGreaterThanOrEqualTo(String value) {
            addCriterion("sendiden >=", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenLessThan(String value) {
            addCriterion("sendiden <", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenLessThanOrEqualTo(String value) {
            addCriterion("sendiden <=", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenLike(String value) {
            addCriterion("sendiden like", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenNotLike(String value) {
            addCriterion("sendiden not like", value, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenIn(List<String> values) {
            addCriterion("sendiden in", values, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenNotIn(List<String> values) {
            addCriterion("sendiden not in", values, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenBetween(String value1, String value2) {
            addCriterion("sendiden between", value1, value2, "sendiden");
            return (Criteria) this;
        }

        public Criteria andSendidenNotBetween(String value1, String value2) {
            addCriterion("sendiden not between", value1, value2, "sendiden");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
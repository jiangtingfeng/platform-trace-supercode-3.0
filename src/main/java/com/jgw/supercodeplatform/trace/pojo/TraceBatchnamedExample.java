package com.jgw.supercodeplatform.trace.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraceBatchnamedExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TraceBatchnamedExample() {
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
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFieldidIsNull() {
            addCriterion("FieldId is null");
            return (Criteria) this;
        }

        public Criteria andFieldidIsNotNull() {
            addCriterion("FieldId is not null");
            return (Criteria) this;
        }

        public Criteria andFieldidEqualTo(String value) {
            addCriterion("FieldId =", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidNotEqualTo(String value) {
            addCriterion("FieldId <>", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidGreaterThan(String value) {
            addCriterion("FieldId >", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidGreaterThanOrEqualTo(String value) {
            addCriterion("FieldId >=", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidLessThan(String value) {
            addCriterion("FieldId <", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidLessThanOrEqualTo(String value) {
            addCriterion("FieldId <=", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidLike(String value) {
            addCriterion("FieldId like", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidNotLike(String value) {
            addCriterion("FieldId not like", value, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidIn(List<String> values) {
            addCriterion("FieldId in", values, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidNotIn(List<String> values) {
            addCriterion("FieldId not in", values, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidBetween(String value1, String value2) {
            addCriterion("FieldId between", value1, value2, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldidNotBetween(String value1, String value2) {
            addCriterion("FieldId not between", value1, value2, "fieldid");
            return (Criteria) this;
        }

        public Criteria andFieldnameIsNull() {
            addCriterion("FieldName is null");
            return (Criteria) this;
        }

        public Criteria andFieldnameIsNotNull() {
            addCriterion("FieldName is not null");
            return (Criteria) this;
        }

        public Criteria andFieldnameEqualTo(String value) {
            addCriterion("FieldName =", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameNotEqualTo(String value) {
            addCriterion("FieldName <>", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameGreaterThan(String value) {
            addCriterion("FieldName >", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameGreaterThanOrEqualTo(String value) {
            addCriterion("FieldName >=", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameLessThan(String value) {
            addCriterion("FieldName <", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameLessThanOrEqualTo(String value) {
            addCriterion("FieldName <=", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameLike(String value) {
            addCriterion("FieldName like", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameNotLike(String value) {
            addCriterion("FieldName not like", value, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameIn(List<String> values) {
            addCriterion("FieldName in", values, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameNotIn(List<String> values) {
            addCriterion("FieldName not in", values, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameBetween(String value1, String value2) {
            addCriterion("FieldName between", value1, value2, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldnameNotBetween(String value1, String value2) {
            addCriterion("FieldName not between", value1, value2, "fieldname");
            return (Criteria) this;
        }

        public Criteria andFieldcodeIsNull() {
            addCriterion("FieldCode is null");
            return (Criteria) this;
        }

        public Criteria andFieldcodeIsNotNull() {
            addCriterion("FieldCode is not null");
            return (Criteria) this;
        }

        public Criteria andFieldcodeEqualTo(String value) {
            addCriterion("FieldCode =", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeNotEqualTo(String value) {
            addCriterion("FieldCode <>", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeGreaterThan(String value) {
            addCriterion("FieldCode >", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeGreaterThanOrEqualTo(String value) {
            addCriterion("FieldCode >=", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeLessThan(String value) {
            addCriterion("FieldCode <", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeLessThanOrEqualTo(String value) {
            addCriterion("FieldCode <=", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeLike(String value) {
            addCriterion("FieldCode like", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeNotLike(String value) {
            addCriterion("FieldCode not like", value, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeIn(List<String> values) {
            addCriterion("FieldCode in", values, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeNotIn(List<String> values) {
            addCriterion("FieldCode not in", values, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeBetween(String value1, String value2) {
            addCriterion("FieldCode between", value1, value2, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andFieldcodeNotBetween(String value1, String value2) {
            addCriterion("FieldCode not between", value1, value2, "fieldcode");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("CreateDate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("CreateDate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("CreateDate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("CreateDate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("CreateDate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateDate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("CreateDate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("CreateDate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("CreateDate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("CreateDate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("CreateDate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("CreateDate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIsNull() {
            addCriterion("UpdateDate is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIsNotNull() {
            addCriterion("UpdateDate is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedateEqualTo(Date value) {
            addCriterion("UpdateDate =", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotEqualTo(Date value) {
            addCriterion("UpdateDate <>", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateGreaterThan(Date value) {
            addCriterion("UpdateDate >", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateDate >=", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateLessThan(Date value) {
            addCriterion("UpdateDate <", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateLessThanOrEqualTo(Date value) {
            addCriterion("UpdateDate <=", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIn(List<Date> values) {
            addCriterion("UpdateDate in", values, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotIn(List<Date> values) {
            addCriterion("UpdateDate not in", values, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateBetween(Date value1, Date value2) {
            addCriterion("UpdateDate between", value1, value2, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotBetween(Date value1, Date value2) {
            addCriterion("UpdateDate not between", value1, value2, "updatedate");
            return (Criteria) this;
        }

        public Criteria andFunidIsNull() {
            addCriterion("FunId is null");
            return (Criteria) this;
        }

        public Criteria andFunidIsNotNull() {
            addCriterion("FunId is not null");
            return (Criteria) this;
        }

        public Criteria andFunidEqualTo(String value) {
            addCriterion("FunId =", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidNotEqualTo(String value) {
            addCriterion("FunId <>", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidGreaterThan(String value) {
            addCriterion("FunId >", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidGreaterThanOrEqualTo(String value) {
            addCriterion("FunId >=", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidLessThan(String value) {
            addCriterion("FunId <", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidLessThanOrEqualTo(String value) {
            addCriterion("FunId <=", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidLike(String value) {
            addCriterion("FunId like", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidNotLike(String value) {
            addCriterion("FunId not like", value, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidIn(List<String> values) {
            addCriterion("FunId in", values, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidNotIn(List<String> values) {
            addCriterion("FunId not in", values, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidBetween(String value1, String value2) {
            addCriterion("FunId between", value1, value2, "funid");
            return (Criteria) this;
        }

        public Criteria andFunidNotBetween(String value1, String value2) {
            addCriterion("FunId not between", value1, value2, "funid");
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
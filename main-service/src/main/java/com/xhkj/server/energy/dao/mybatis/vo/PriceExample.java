package com.xhkj.server.energy.dao.mybatis.vo;

import java.util.ArrayList;
import java.util.List;

public class PriceExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table data_price
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table data_price
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table data_price
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public PriceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_price
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table data_price
     *
     * @mbg.generated
     */
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

        public Criteria andCompanyIdIsNull() {
            addCriterion("COMPANY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("COMPANY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(Integer value) {
            addCriterion("COMPANY_ID =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(Integer value) {
            addCriterion("COMPANY_ID <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(Integer value) {
            addCriterion("COMPANY_ID >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("COMPANY_ID >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(Integer value) {
            addCriterion("COMPANY_ID <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(Integer value) {
            addCriterion("COMPANY_ID <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<Integer> values) {
            addCriterion("COMPANY_ID in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<Integer> values) {
            addCriterion("COMPANY_ID not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(Integer value1, Integer value2) {
            addCriterion("COMPANY_ID between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("COMPANY_ID not between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceIsNull() {
            addCriterion("FT3Q_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceIsNotNull() {
            addCriterion("FT3Q_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceEqualTo(Float value) {
            addCriterion("FT3Q_PRICE =", value, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceNotEqualTo(Float value) {
            addCriterion("FT3Q_PRICE <>", value, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceGreaterThan(Float value) {
            addCriterion("FT3Q_PRICE >", value, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceGreaterThanOrEqualTo(Float value) {
            addCriterion("FT3Q_PRICE >=", value, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceLessThan(Float value) {
            addCriterion("FT3Q_PRICE <", value, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceLessThanOrEqualTo(Float value) {
            addCriterion("FT3Q_PRICE <=", value, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceIn(List<Float> values) {
            addCriterion("FT3Q_PRICE in", values, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceNotIn(List<Float> values) {
            addCriterion("FT3Q_PRICE not in", values, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceBetween(Float value1, Float value2) {
            addCriterion("FT3Q_PRICE between", value1, value2, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andFt3qPriceNotBetween(Float value1, Float value2) {
            addCriterion("FT3Q_PRICE not between", value1, value2, "ft3qPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceIsNull() {
            addCriterion("JQI_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andJqiPriceIsNotNull() {
            addCriterion("JQI_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andJqiPriceEqualTo(Float value) {
            addCriterion("JQI_PRICE =", value, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceNotEqualTo(Float value) {
            addCriterion("JQI_PRICE <>", value, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceGreaterThan(Float value) {
            addCriterion("JQI_PRICE >", value, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceGreaterThanOrEqualTo(Float value) {
            addCriterion("JQI_PRICE >=", value, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceLessThan(Float value) {
            addCriterion("JQI_PRICE <", value, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceLessThanOrEqualTo(Float value) {
            addCriterion("JQI_PRICE <=", value, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceIn(List<Float> values) {
            addCriterion("JQI_PRICE in", values, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceNotIn(List<Float> values) {
            addCriterion("JQI_PRICE not in", values, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceBetween(Float value1, Float value2) {
            addCriterion("JQI_PRICE between", value1, value2, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andJqiPriceNotBetween(Float value1, Float value2) {
            addCriterion("JQI_PRICE not between", value1, value2, "jqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceIsNull() {
            addCriterion("QQI_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andQqiPriceIsNotNull() {
            addCriterion("QQI_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andQqiPriceEqualTo(Float value) {
            addCriterion("QQI_PRICE =", value, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceNotEqualTo(Float value) {
            addCriterion("QQI_PRICE <>", value, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceGreaterThan(Float value) {
            addCriterion("QQI_PRICE >", value, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceGreaterThanOrEqualTo(Float value) {
            addCriterion("QQI_PRICE >=", value, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceLessThan(Float value) {
            addCriterion("QQI_PRICE <", value, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceLessThanOrEqualTo(Float value) {
            addCriterion("QQI_PRICE <=", value, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceIn(List<Float> values) {
            addCriterion("QQI_PRICE in", values, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceNotIn(List<Float> values) {
            addCriterion("QQI_PRICE not in", values, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceBetween(Float value1, Float value2) {
            addCriterion("QQI_PRICE between", value1, value2, "qqiPrice");
            return (Criteria) this;
        }

        public Criteria andQqiPriceNotBetween(Float value1, Float value2) {
            addCriterion("QQI_PRICE not between", value1, value2, "qqiPrice");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table data_price
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table data_price
     *
     * @mbg.generated
     */
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


    ////*******自定义开始********/
    //***********自定义结束****////
}

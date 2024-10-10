package com.centroid.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class WhereExpression {

    protected QueryElement<String, Object> currentElement;
    protected String logicalOperator = "$and";

    @JsonProperty("$where")
    protected QueryElement<String, Object> whereElement;

    @JsonIgnore()
    public QueryElement<String, Object> getWhere() {
        return this.whereElement;
    }

    public WhereExpression(String field) {
        this.currentElement = new QueryElement<String, Object>(field, 1);
    }

    public WhereExpression equal(Object value) {
        return this.appendWhere(this.currentElement, "$eq", value);
    }

    public WhereExpression notEqual(Object value) {
        return this.appendWhere(this.currentElement, "$ne", value);
    }

    public WhereExpression greaterThan(Object value) {
        return this.appendWhere(this.currentElement, "$gt", value);
    }

    public WhereExpression greaterThanOrEqual(Object value) {
        return this.appendWhere(this.currentElement, "$gte", value);
    }

    public WhereExpression lowerThan(Object value) {
        return this.appendWhere(this.currentElement, "$lt", value);
    }

    public WhereExpression lowerThanOrEqual(Object value) {
        return this.appendWhere(this.currentElement, "$lte", value);
    }

    public WhereExpression in(Object[] value) {
        return this.appendWhere(this.currentElement, "$in", value);
    }

    private WhereExpression appendWhere(Object left, String operator, Object right) {
        if (left == null) {
            throw new IllegalArgumentException("left operand cannot be null");
        }
        QueryElement<String, Object> expr = new QueryElement<String, Object>(
            operator, new Object[]{
                left,
                right
            }
        );

        if (this.whereElement == null) {
            this.whereElement = expr;
        } else {
            this.whereElement = new QueryElement<String, Object>(this.logicalOperator, new Object[]{
                this.whereElement,
                expr
            });
        }
        return this;
    }

    public WhereExpression and(String field) {
        this.logicalOperator = "$and";
        this.currentElement = new QueryElement<String, Object>(field, 1);
        return this;
    }

    public WhereExpression or(String field) {
        this.logicalOperator = "$or";
        this.currentElement = new QueryElement<String, Object>(field, 1);
        return this;
    }

}

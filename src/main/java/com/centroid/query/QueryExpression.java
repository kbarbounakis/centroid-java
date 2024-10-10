package com.centroid.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.reactivex.rxjava3.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

@JsonInclude(Include.NON_NULL)
public class QueryExpression {

    private QueryElement<String, Object> currentElement;
    private String logicalOperator = "$and";

    @JsonProperty("$select")
    protected QueryElemenCollection<String, Object> selectElement = new QueryElemenCollection<String, Object>();

    @JsonProperty("$orderBy")
    @Nullable
    protected ArrayList<OrderByElement> orderByElement;

    @JsonProperty("$groupBy")
    @Nullable
    protected QueryElemenCollection<String, Object> groupByElement;

    protected Integer skip;

    @JsonProperty("$skip")
    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    protected Integer limit;

    @JsonProperty("$limit")
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @JsonProperty("$collection")
    protected String collection;

    @JsonProperty("$where")
    protected QueryElement<String, Object> whereElement;

    protected QueryElement<String, Object> preparedElement;

    public QueryExpression() {

    }

    public QueryExpression from(String collection) {
        this.collection = collection;
        return this;
    }

    @JsonIgnore()
    public QueryElemenCollection<String, Object> getSelect() {
        return this.selectElement;
    }

    public QueryExpression select(String... fields) {
        Arrays.stream(fields).forEach(field -> {
            this.selectElement.put(field, 1);
        });
        return this;
    }

    public QueryExpression select(QueryField... fields) {
        Arrays.stream(fields).forEach(field -> {
            this.selectElement.put(field.getKey(), field.getValue());
        });
        return this;
    }

    /**
     * Adds an ordering element to the query expression.
     *
     * @param field the field by which the results should be ordered
     * @return the updated QueryExpression object with the new ordering element
     */
    public QueryExpression orderBy(String field) {
        this.orderByElement = new ArrayList<OrderByElement>();
        return this.appendOrderBy(field, OrderDirection.ASC);
    }

    /**
     * Adds a field to the query's order by clause in descending order.
     *
     * @param field the name of the field to order by
     * @return the updated QueryExpression object with the specified field added to the order by clause in descending order
     */
    public QueryExpression orderByDescending(String field) {
        this.orderByElement = new ArrayList<OrderByElement>();
        return this.appendOrderBy(field, OrderDirection.DESC);
    }

    public QueryExpression thenBy(String field) {
        return this.appendOrderBy(field, OrderDirection.ASC);
    }

    public QueryExpression thenByDescending(String field) {
        return this.appendOrderBy(field, OrderDirection.DESC);
    }

    private QueryExpression appendOrderBy(String field, OrderDirection direction) {
        if (this.orderByElement == null) {
            this.orderByElement = new ArrayList<OrderByElement>();
        }
        this.orderByElement.add(new OrderByElement(field, direction));
        return this;
    }

    @JsonIgnore()
    public ArrayList<OrderByElement> getOrderBy() {
        return this.orderByElement;
    }

    public QueryExpression groupBy(QueryField... fields) {
        if (this.groupByElement == null) {
            this.groupByElement = new QueryElemenCollection<String, Object>();
        }
        Arrays.stream(fields).forEach(field -> {
            this.groupByElement.put(field.getKey(), field.getValue());
        });
        return this;
    }


    @JsonIgnore()
    public QueryElemenCollection<String, Object> getGroupBy() {
        return this.groupByElement;
    }


    public QueryExpression skip(int skip) {
        this.skip = skip;
        return this;
    }

    public QueryExpression take(int limit) {
        this.limit = limit;
        return this;
    }

    public QueryExpression where(String field) {
        this.currentElement = new QueryElement<String, Object>(field, 1);
        return this;
    }

    public QueryExpression where(WhereExpression expr) {
        this.whereElement = expr.getWhere();
        return this;
    }

    @JsonIgnore()
    public QueryElement<String, Object> getWhere() {
        return this.whereElement;
    }

    private QueryExpression appendWhere(Object left, String operator, Object right) {
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

    public QueryExpression equal(Object value) {
        return this.appendWhere(this.currentElement, "$eq", value);
    }

    public QueryExpression notEqual(Object value) {
        return this.appendWhere(this.currentElement, "$ne", value);
    }

    public QueryExpression greaterThan(Object value) {
        return this.appendWhere(this.currentElement, "$gt", value);
    }

    public QueryExpression greaterThanOrEqual(Object value) {
        return this.appendWhere(this.currentElement, "$gte", value);
    }

    public QueryExpression lowerThan(Object value) {
        return this.appendWhere(this.currentElement, "$lt", value);
    }

    public QueryExpression lowerThanOrEqual(Object value) {
        return this.appendWhere(this.currentElement, "$lte", value);
    }

    public QueryExpression in(Object[] value) {
        return this.appendWhere(this.currentElement, "$in", value);
    }

    public QueryExpression notIn(Object[] value) {
        return this.appendWhere(this.currentElement, "$nin", value);
    }

    public QueryExpression and(String field) {
        this.logicalOperator = "$and";
        this.currentElement = new QueryElement<String, Object>(field, 1);
        return this;
    }

    public QueryExpression or(String field) {
        this.logicalOperator = "$or";
        this.currentElement = new QueryElement<String, Object>(field, 1);
        return this;
    }

}

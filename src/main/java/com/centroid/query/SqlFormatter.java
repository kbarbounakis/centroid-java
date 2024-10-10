package com.centroid.query;

public class SqlFormatter {

    protected SqlDialect sqlDialect;

    public SqlFormatter(SqlDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    public String format(QueryExpression expr) {
        if (expr.getSelect() != null) {
            if (expr.getLimit() != null) {
                return formatLimitSelect(expr);
            }
            return this.formatSelect(expr);
        }
        return null;
    }

    public String formatSelect(QueryExpression expr) {
        return null;
    }

    public String formatLimitSelect(QueryExpression expr) {
        return null;
    }

    public String formatOrderBy(QueryExpression expr) {
        return null;
    }

    public String formatGroupBy(QueryExpression expr) {
        return null;
    }

    public String formatInsert(QueryExpression expr) {
        return null;
    }

    public String formatUpdate(QueryExpression expr) {
        return null;
    }

    public String formatDelete(QueryExpression expr) {
        return null;
    }

}

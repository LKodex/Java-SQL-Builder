package io.github.lkodex;

import java.util.Iterator;
import java.util.List;

public class SQLBuilder {
    private StringBuilder query;
    private boolean from;
    private List<String> selectColumns;
    private String fromTable;
    private List<String> whereConditions;
    private String groupByColumn;
    private List<String> orderByColumns;

    public SQLBuilder() {
        final int INITIAL_CHAR_CAPACITY = 512;
        query = new StringBuilder(INITIAL_CHAR_CAPACITY);
        from = false;
    }
    
    public SQLBuilder select() {
        return select("*");
    }

    public SQLBuilder select(String... columns) {
        selectColumns = List.of(columns);
        return this;
    }
    
    public SQLBuilder from(String table) {
        from = true;
        fromTable = table;
        return this;
    }
    
    public SQLBuilder where(String... conditions) {
        whereConditions = List.of(conditions);
        return this;
    }
    
    public SQLBuilder groupBy(String column) {
        groupByColumn = column;
        return this;
    }
    
    public SQLBuilder orderBy(String columns) {
        orderByColumns = List.of(columns);
        return this;
    }

    public String query() {
        final String WHITESPACE = " ";

        boolean hasSelectStatement = !selectColumns.isEmpty();
        if (hasSelectStatement) {
            query.append("SELECT");
            Iterator<String> selectColumnsIterator = selectColumns.iterator();
            while(selectColumnsIterator.hasNext()) {
                String column = selectColumnsIterator.next();
                query.append(WHITESPACE);
                query.append(column);
                if (selectColumnsIterator.hasNext()) {
                    query.append(",");
                }
            }
        }

        if (from) {
            String fromQuery = String.format(" FROM %s", fromTable);
            query.append(fromQuery);
        }

        boolean isNotEmptyWhereConditions = !whereConditions.isEmpty();
        if (isNotEmptyWhereConditions) {
            query.append(" WHERE");
            Iterator<String> whereConditionsIterator = whereConditions.iterator();
            while(whereConditionsIterator.hasNext()) {
                String condition = whereConditionsIterator.next();
                query.append(WHITESPACE);
                query.append(condition);
                if(whereConditionsIterator.hasNext()) {
                    query.append(" AND");
                }
            }
        }

        boolean hasGroupingCriteria = groupByColumn != null;
        if (hasGroupingCriteria) {
            String groupQuery = String.format(" GROUP BY %s", groupByColumn);
            query.append(groupQuery);
        }

        boolean hasOrderingCriteria = !orderByColumns.isEmpty();
        if (hasOrderingCriteria) {
            query.append(" ORDER BY");
            Iterator<String> orderByColumnsIterator = orderByColumns.iterator();
            while(orderByColumnsIterator.hasNext()) {
                String column = orderByColumnsIterator.next();
                query.append(WHITESPACE);
                query.append(column);
                if(orderByColumnsIterator.hasNext()) {
                    query.append(",");
                }
            }
        }

        return query.toString();
    }
}

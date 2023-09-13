package io.github.lkodex;

public class Test {
    public static void main(String... args) {
        testBuiltQueryMatchManuallyWroteQuery();
        testDifferentOrderBuildTheSameQuery();
    }
    
    public static void testBuiltQueryMatchManuallyWroteQuery() {
        String wroteQuery = "SELECT id, name, balance FROM customer WHERE balance < 399 ORDER BY name";
        SQLBuilder queryBuilder = new SQLBuilder();
        String query = queryBuilder
            .from("customer")
            .select("id", "name", "balance")
            .orderBy("name")
            .where("balance < 399")
            .query();
        assert wroteQuery.equals(query);
    }

    public static void testDifferentOrderBuildTheSameQuery() {
        SQLBuilder firstQueryBuilder = new SQLBuilder();
        String firstQuery = firstQueryBuilder
            .select()
            .from("customer")
            .where("balance > 1000", "credit > 100")
            .groupBy("country")
            .orderBy("name")
            .query();
            
        SQLBuilder secondQueryBuilder = new SQLBuilder();
        String secondQuery = secondQueryBuilder
            .orderBy("name")
            .groupBy("country")
            .where("balance > 1000", "credit > 100")
            .from("customer")
            .select()
            .query();
        assert firstQuery.equals(secondQuery);
    }
}

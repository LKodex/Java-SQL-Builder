package io.github.lkodex;

public class Main {
    public static void main(String... args) {
        SQLBuilder queryBuilder = new SQLBuilder();

        String query = queryBuilder
            .select("uuid", "name", "type")
            .from("item")
            .where("value > 10")
            .groupBy("type")
            .orderBy("name")
            .query();
        
        String output = String.format("Built SQL query:\n%s", query);
        System.out.println(output);
    }
}

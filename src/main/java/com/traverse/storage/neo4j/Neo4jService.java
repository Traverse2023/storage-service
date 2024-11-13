package com.traverse.storage.neo4j;
import com.traverse.storage.models.CollectionSearchCriteria;
import com.traverse.storage.models.FieldSearchCriteria;
import com.traverse.storage.models.Neo4jNode;
import org.neo4j.driver.Session;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author isfar
 *
 * This is the Neo4j Service where standardized search and patch implementations are defined
 * For any Neo4j collection we have, this service can be used to search through the items or
 * patch
 * @param <T> generic type - for example, User or Group
 */
@Service
public class Neo4jService<T> {

    @Autowired
    private Driver driver;

    /**
     * This search function allows users to give a formatted criteria and collection. Then query that
     * neo4j collection based on that criteria.
     * @param entityClass
     * @param criteria
     * @return a list of Neo4jNodes
     */
    public List<Neo4jNode> search(Class<T> entityClass, CollectionSearchCriteria criteria) {
        List<Neo4jNode> results = new ArrayList<>();

        try (Session session = driver.session()) {
            StringBuilder query;
            if (criteria.getText() != null) {
                query = new StringBuilder();
                query.append(criteria.getFilter() ?
                        "CALL db.index.fulltext.queryNodes('textIndex', " + "'" + criteria.getText() + "'" + ") YIELD node AS n WHERE " :
                        "CALL db.index.fulltext.queryNodes('textIndex', " + "'" + criteria.getText() + "'" + ") YIELD node AS n RETURN n UNION " + "MATCH (n:" + entityClass.getSimpleName() + ") WHERE ");
            } else {
                query = new StringBuilder("MATCH (n:" + entityClass.getSimpleName() + ") WHERE ");
            }

            List<String> conditions = new ArrayList<>();
            for (FieldSearchCriteria fieldCriteria : criteria.getFieldSearchCriterias()) {
                String condition;
                switch (fieldCriteria.getSearchMode()) {
                    case IN:
                        List<String> vals = new ArrayList<>();

                        for (Object val : fieldCriteria.getValues()) {
                            vals.add("'" + val + "'");
                        }
                        condition = "n." + fieldCriteria.getField() + " IN " + vals.toString();
                        break;
                    //TODO Allow more reange types then just dates
                    case RANGE:
                        List<Object> values = fieldCriteria.getValues();
                        if (values.size() == 2) {
                            condition = "n." + fieldCriteria.getField() + " >= datetime('" + values.get(0) + "') AND n." + fieldCriteria.getField() + " <= datetime('" + values.get(1) + "')";
                        } else {
                            continue;
                        }
                        break;
                    default:
                        continue;
                }
                conditions.add(condition);
            }

            if (!conditions.isEmpty()) {
                query.append(String.join(criteria.getFilter() ? " AND " : " OR ", conditions));
            } else {
                query.append("1 = 1");
            }

            query.append(" RETURN n");

            Result result = session.run(query.toString());

            while (result.hasNext()) {
                Record record = result.next();
                Neo4jNode customNode = new Neo4jNode();
                Node node = record.values().get(0).asNode();
                customNode.setElementId(node.elementId());
                customNode.setType(node.labels().iterator().next());
                customNode.setProperties(node.asMap());
                results.add(customNode);
            }
        }

        return results;
    }

    //TODO complete patch implementation
    public void patch() {

    }
}
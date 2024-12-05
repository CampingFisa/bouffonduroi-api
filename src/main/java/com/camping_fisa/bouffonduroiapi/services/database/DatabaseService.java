package com.camping_fisa.bouffonduroiapi.services.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getAllTableNames() {
        return entityManager.createNativeQuery(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'"
        ).getResultList();
    }

    public List<Object[]> getTableData(String tableName) {
        Query query = entityManager.createNativeQuery("SELECT * FROM " + tableName);
        return query.getResultList();
    }

    public Map<String, List<Object[]>> getAllTablesAndData() {
        List<String> tableNames = getAllTableNames();
        Map<String, List<Object[]>> result = new HashMap<>();
        for (String tableName : tableNames) {
            result.put(tableName, getTableData(tableName));
        }
        return result;
    }
}

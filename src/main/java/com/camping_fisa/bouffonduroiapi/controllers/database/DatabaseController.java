package com.camping_fisa.bouffonduroiapi.controllers.database;

import com.camping_fisa.bouffonduroiapi.services.database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/tables")
    public ResponseEntity<Map<String, List<Object[]>>> getAllTablesAndData() {
        return ResponseEntity.ok(databaseService.getAllTablesAndData());
    }
}


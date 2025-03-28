package com.rooter.cointracker.controller;

import com.rooter.cointracker.model.User;
import com.rooter.cointracker.service.AdminService;
import com.rooter.cointracker.service.BackupRestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BackupRestoreService backupRestoreService;

    @GetMapping("/ping")
    public String getPing() {
        return "ping";
    }
    

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportData() {
        String exportedData = adminService.exportData();
        return ResponseEntity.ok(exportedData);
    }

    @GetMapping("/monitor")
    public ResponseEntity<String> monitorSystem() {
        String systemStatus = adminService.monitorSystem();
        return ResponseEntity.ok(systemStatus);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = adminService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = adminService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/backup/csv")
    public ResponseEntity<String> backupDataAsCSV() throws IOException {
        String csvData = backupRestoreService.exportDataAsCSV();
        return ResponseEntity.ok(csvData);
    }

    @GetMapping("/backup/json")
    public ResponseEntity<String> backupDataAsJSON() throws IOException {
        String jsonData = backupRestoreService.exportDataAsJSON();
        return ResponseEntity.ok(jsonData);
    }

    @PostMapping("/restore/csv")
    public ResponseEntity<Void> restoreDataFromCSV(@RequestBody String csvData) throws IOException {
        backupRestoreService.importDataFromCSV(csvData);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restore/json")
    public ResponseEntity<Void> restoreDataFromJSON(@RequestBody String jsonData) throws IOException {
        backupRestoreService.importDataFromJSON(jsonData);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/me")
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = adminService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }
    
}
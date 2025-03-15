package com.rooter.cointracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rooter.cointracker.model.User;
import com.rooter.cointracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BackupRestoreService {

    @Autowired
    private UserRepository userRepository;

    public String exportDataAsCSV() throws IOException {
        List<User> users = userRepository.findAll();
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();
        return csvMapper.writer(schema).writeValueAsString(users);
    }

    public String exportDataAsJSON() throws IOException {
        List<User> users = userRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(users);
    }

    public void importDataFromCSV(String csvData) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();
        List<User> users = (List<User>) (List<?>) csvMapper.readerFor(User.class).with(schema).readValues(csvData).readAll();
        userRepository.saveAll(users);
    }

    public void importDataFromJSON(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
        userRepository.saveAll(users);
    }
}
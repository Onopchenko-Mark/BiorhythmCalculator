package model;

import java.util.List;

public interface RecordDaoInterface {
    void createTable();

    void insertResults(RecordModel record);

    List<String[]> getResults();

    void deleteResults();
}
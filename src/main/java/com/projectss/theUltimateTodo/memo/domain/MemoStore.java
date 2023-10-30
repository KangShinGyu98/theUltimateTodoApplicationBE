package com.projectss.theUltimateTodo.memo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "memo_stores")
public class MemoStore {

    @Id
    private String id;

    @Indexed
    private String email;

    @DBRef
    private List<Directory> directories = new ArrayList<>();

    public MemoStore(String email) {
        this.email = email;
    }

    public void saveDirectory(Directory directory) {
        this.directories.add(directory);
    }

    public void deleteDirectory(Directory directory) {
        this.directories.remove(directory);
    }
}

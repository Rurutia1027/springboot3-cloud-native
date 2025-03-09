package com.cloud.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookInfo {

    // we declare two different level views
    // one for presenting only {id and name} as response
    public interface BookListView {
    }

    // one for presenting all {id, name, and contents} as response
    public interface BookDetailView extends BookListView {
    }

    @JsonView(BookListView.class)
    private Long id;

    @JsonView(BookListView.class)
    private String name;

    @JsonView(BookDetailView.class)
    private String content;
}

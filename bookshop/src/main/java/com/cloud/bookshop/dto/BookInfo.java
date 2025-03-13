package com.cloud.bookshop.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @NotBlank
    @JsonView(BookDetailView.class)
    private String content;

    @JsonView(BookListView.class)
    private Date publishDate;
}

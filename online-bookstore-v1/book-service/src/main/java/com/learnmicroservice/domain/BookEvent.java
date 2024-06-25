package com.learnmicroservice.domain;

import com.learnmicroservice.entity.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BookEvent(
        int bookEventId,
        BookEventType bookEventType,
        @NotNull @Valid
        Book book
) {
}

package com.hyperglance.crawler.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrudEvent<T> {




    public enum CrudOperation{
        POST,
        PUT,
        PATCH,
        GET,
        DELETE
    }

}

package com.kushPmakwana.mangia.Mangia.utility;

public interface Mappers<T, REQ, RES> {
    T toEntity(REQ req);
    RES toResponse(T entity);
}

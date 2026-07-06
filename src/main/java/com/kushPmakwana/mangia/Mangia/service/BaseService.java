package com.kushPmakwana.mangia.Mangia.service;

import com.kushPmakwana.mangia.Mangia.exceptions.ResourcesNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<T, R extends JpaRepository<T, Long>, REQ, RES>{


    protected final R repository;

    protected BaseService(R repository){
        this.repository = repository;
    }

    public abstract RES toResponse(T entity);
    public abstract T toEntity(REQ request);

    public List<RES> getAll(){
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    protected T findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourcesNotFoundException(
                                getEntityName() + " not found",
                                getEntityName()
                        ));
    }

    public RES getById(Long id){
        return toResponse(findEntityById(id));
    }

    protected abstract String getEntityName();
}

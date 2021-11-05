package com.creditsuisse.assignment.repository;

import com.creditsuisse.assignment.dao.SimpleEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleEventRepository extends CrudRepository<SimpleEvent, String> {
}
package com.creditsuisse.assignment.repository;

import com.creditsuisse.assignment.dao.ApplicationEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationEventRepository extends CrudRepository<ApplicationEvent, String> {
}

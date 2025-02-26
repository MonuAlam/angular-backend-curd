package com.curd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curd.model.entity.Notes;
import com.curd.model.response.NotesDto;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {

	List<Notes> findByUserId(Integer id);


}

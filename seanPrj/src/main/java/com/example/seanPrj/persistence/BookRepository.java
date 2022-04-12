package com.example.seanPrj.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.seanPrj.model.BookEntity;

// JpaRepository 상속 받은 BookRepository 인터페이스 
// JpaRepository: save, findById, findAll 등이 기본 제공되는 인터페이스에 해당
@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
	List<BookEntity> findByUserId(String userId);
	List<BookEntity> findByTitle(String title);
	// findById
	// findAll 이미 있음
}
package com.example.seanPrj.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.seanPrj.dto.BookDTO;
import com.example.seanPrj.dto.ResponseDTO;
import com.example.seanPrj.model.BookEntity;
import com.example.seanPrj.service.BookService;

@RestController
@RequestMapping("book")
public class BookController {
	@Autowired
	private BookService service;
	
	@PostMapping
	public ResponseEntity<?> createBook(@RequestBody BookDTO dto) {
		try {			
			BookEntity entity = BookDTO.toEntity(dto); 
			entity.setId(null); // 생성 당시 id는 없어야 하므로 id를 null로 초기화
			List<BookEntity> entities = service.create(entity);
			List<BookDTO> dtos =entities.stream().map(BookDTO::new).collect(Collectors.toList()); // entity -> dto 변
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveBookList(@RequestBody BookDTO dto) {
		BookEntity entity = BookDTO.toEntity(dto); // dto -> entity (id 외의 속성들 null) 
		List<BookEntity> entities = service.retrieve(entity.getTitle()); // title 속성 값을 Service 계층에 넘김
		List<BookDTO> dtos =entities.stream().map(BookDTO::new).collect(Collectors.toList()); // entity -> dto 변환 
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateBook(@RequestBody BookDTO dto) {
		BookEntity entity = BookDTO.toEntity(dto);  // 
		List<BookEntity> entities = service.update(entity);
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);	
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody BookDTO dto) {
		try {
			BookEntity entity = BookDTO.toEntity(dto);  //
			List<BookEntity> entities = service.delete(entity);
			List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}
};

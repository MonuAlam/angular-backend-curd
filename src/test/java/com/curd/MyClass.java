//package com.curd;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.List;
//
//import org.assertj.core.error.ShouldBe;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.curd.controller.NotesController;
//
//import jakarta.validation.constraints.AssertTrue;
//
//@SpringBootTest
//public class MyClass {
//	
//	@Autowired
//	NotesController controller;
//
//	@Test
//	public void ShouldUserExist(){
//		
//		List dataList=controller.getAllNotes();
//		
//		assertTrue(dataList.size()>0);
//		assertFalse(dataList.isEmpty());
//	}
//	
//}

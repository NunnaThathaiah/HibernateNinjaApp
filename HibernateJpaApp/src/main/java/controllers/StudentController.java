package controllers;

import java.util.logging.Logger;

import com.google.inject.Inject;

import dao.StudentDao;
import filters.RequestAuthorization;
import models.Student;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.DELETE;
import ninja.jaxy.GET;
import ninja.jaxy.PATCH;
import ninja.jaxy.POST;
import ninja.jaxy.PUT;
import ninja.jaxy.Path;
import ninja.params.PathParam;

@Path("/student")
@FilterWith(RequestAuthorization.class)
public class StudentController {
	Logger log = Logger.getLogger(StudentController.class.getName());

	@Inject
	StudentDao studentDao;

	@Inject
	Student student;

	@GET
	@Path("/getStudents")
	public Result getStudent() {
		// call studentDao method to get All Students from DB
		return Results.json().render(studentDao.getAllStudents());
	}

	@POST
	@Path("/saveStudent")
	public Result saveStudent(Student student) {

		System.out.println(student);
		// call studentDao method to save Students into DB
		//System.out.println(student);
		int result = studentDao.saveStudent(student);
		return Results.json().render(result);
	}

	@PUT
	@Path("/updateStudent")
	public Result updateStudent(Student student) {
		// StudentsDto studentDto =null;
		boolean result = studentDao.saveOrUpdateStudent(student);

		if (result == false) {
			student = studentDao.findStudentById(student.getSid());

		}
		return Results.json().render(student);

	}

	/*@DELETE
	@Path("/delete")
	public Result deleteStudents(Student student) {
		//send the student data to studentDao for delete operation
		boolean result = studentDao.delete(student);
		return Results.json().render(student);
	}*/

	@GET
	@Path("/findStudent/{id}")
	public Result getStudentById(@PathParam("id") Long sid) {
		//getting Student data from StudentDao
		Student student = studentDao.findStudentById(sid);

		return Results.json().render(student);

	}

	
	  @PUT
	  @Path("/updateStudentById/{id}")
	  public Result updateStudentById(@PathParam("id") Long sid,Student student) {
	 student.setSid(sid);
	 
	 Boolean result = studentDao.saveOrUpdateStudent(student); return
	  Results.json().render(result); }
	 
	
	 @DELETE
	 @Path("/delete/{id}") public Result deleteStudent(@PathParam("id") Long
	 sid,Student student) { 
		 student.setSid(sid);
		 Boolean result =
	 studentDao.delete(student); return Results.json().render(student); }
	 

	/*
	 * @POST
	 * 
	 * @Path("/saveStudent/{id}/{name}/{age}") public Result
	 * saveStudent(@PathParam("id") int sid,@PathParam("name") String
	 * name,@PathParam("age") int age ) { Student student1 = new Student();
	 * student1.setSid(sid); student1.setSname(name); student1.setAge(age);
	 * System.out.println(student1); int result =studentDao.saveStudent(student1);
	 * return Results.json().render(result); }
	 */

}

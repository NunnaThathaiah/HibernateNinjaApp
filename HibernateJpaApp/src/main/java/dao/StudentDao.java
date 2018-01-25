package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.eclipse.jetty.util.log.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Article;
import models.ArticlesDto;
import models.Student;
import models.StudentBo;
import models.StudentsDto;
import models.User;
import ninja.jpa.UnitOfWork;

public class StudentDao {
	Logger log = Logger.getLogger(StudentDao.class.getName());
	@Inject
	Provider<EntityManager> entitiyManagerProvider;

	@UnitOfWork
	public List<Student> getAllStudents() {

		EntityManager entityManager = entitiyManagerProvider.get();

		TypedQuery<Student> query = entityManager.createQuery("SELECT X FROM Student X", Student.class);
		List<Student> students = query.getResultList();

		return students;

	}

	@Transactional
	public int saveStudent(Student student) {

		EntityManager em = entitiyManagerProvider.get();
		em.persist(student);
		return 0;
	}

	@Transactional
	public boolean saveOrUpdateStudent(Student student) {
		EntityManager em = entitiyManagerProvider.get();
		em.merge(student);
		return true;
	}

	@Transactional
	public boolean delete(Student object) {
		EntityManager em = entitiyManagerProvider.get();
		Student student = (Student) object;
		 student =em.find(Student.class, student.getSid());
		//student = em.getReference(Student.class, student.getSid());
		System.out.println(student);
		em.remove(student);
		return true;
	}

	@UnitOfWork
	public Student findStudentById(Long sid) {

		EntityManager em = entitiyManagerProvider.get();
		Query q = em.createQuery("SELECT x FROM Student x WHERE x.id = :idParam");
		Student student = null;
		try {
			student = (Student) q.setParameter("idParam", sid).getSingleResult();
		} catch (Exception e) {
			log.warning("Student Does not Exit for Id" + sid);
		}
		return student;
	}

}

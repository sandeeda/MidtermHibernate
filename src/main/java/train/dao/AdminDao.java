package train.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import train.model.Admin;


@Component
public class AdminDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	

	//get all admins
	public List<train.model.Admin> getAdmins(){
		return this.hibernateTemplate.loadAll(Admin.class);
	}
	
	
}

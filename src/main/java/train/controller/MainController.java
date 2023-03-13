package train.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import train.dao.AdminDao;
import train.dao.TrainDao;
import train.model.Admin;
import train.model.Train;

@Controller
public class MainController {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private TrainDao trainDao;

	@RequestMapping("/")
	public String home(Model m) {
		return "login";
	}

	// handle login form
	@RequestMapping(value = "/handle-login", method = RequestMethod.POST)
	public RedirectView handleLogin(@ModelAttribute Admin admin, HttpServletRequest request, Model m) {
		List<Admin> admins = adminDao.getAdmins();
		boolean approved = false;
		for (Iterator iterator = admins.iterator(); iterator.hasNext();) {
			Admin admin2 = (Admin) iterator.next();
			System.out.println(admin2.getUsername());
			System.out.println(admin2.getPassword());
			System.out.println(admin.getPassword());
			System.out.println(admin.getPassword());
			if (admin2.getPassword().equals(admin.getPassword()) && admin.getUsername().equals(admin2.getUsername())) {
				System.out.println("HERE");
				approved = true;
			}

		}
		if (approved) {
			// List<Student> students = studentDao.getStudents();
			// m.addAttribute("students", students);
			m.addAttribute("loggedinuser", admin.getUsername());
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl(request.getContextPath() + "/pathlistoftrains");
			return redirectView;
		} else {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl(request.getContextPath() + "/loginfailed");
			return redirectView;
		}

	}

	@RequestMapping("/loginfailed")
	public String showLoginFailed(Model m) {
		return "error";
	}

	@RequestMapping("/pathlistoftrains")
	public String showListOfAllTrains(Model m) {
		List<Train> allTrains = this.trainDao.getAll();
		m.addAttribute("listoftrains", allTrains);
		return "listoftrains";
	}
	
	@RequestMapping(value = "/handle-filtertrain", method = RequestMethod.POST)
	public String showListOfTrainsBySrcAndDest(@RequestParam String source, @RequestParam String destination,Model m) {
		List<Train> allTrains = this.trainDao.getTrainsBySourceAndDestination(source, destination);
		m.addAttribute("listoftrains", allTrains);
		if(source.equals("") || destination.equals("")) {
			allTrains = this.trainDao.getAll();
			m.addAttribute("listoftrains", allTrains);
		}
		return "listoftrains";
	}
	
	@RequestMapping(value = "/handle-booktrain", method = RequestMethod.POST)
	public String showRegistrationToBookTrain(@RequestParam String trainId,Model m) {
		Train trainById = trainDao.getById(Integer.valueOf(trainId));
		m.addAttribute("trainbyid",trainById);
		return "bookingform";
	}

}

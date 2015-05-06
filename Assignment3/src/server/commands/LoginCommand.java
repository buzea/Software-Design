package server.commands;

import client.model.Doctor;
import client.model.Staff;
import server.mappers.DataSourceException;
import server.mappers.DoctorMapper;
import server.mappers.StaffMapper;

public class LoginCommand implements Command {

	
	private String username,password;
	

	public LoginCommand(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public Object execute() {
		try{
		Doctor doctor = DoctorMapper.getInstance().getDoctor(username);
		if(doctor!=null && doctor.getPassword().equals(password)){
			return doctor;
		} else {
			Staff staff = StaffMapper.getInstance().getStaff(username);
			if(staff!=null && staff.getPassword().equals(password)){
				return staff;
			}
		}
		}catch(DataSourceException e){
			e.printStackTrace();
		}
		return null;
	}

}

package server.commands;

import client.model.Doctor;
import server.mappers.DataSourceException;
import server.mappers.DoctorMapper;

public class UpdateDoctorCommand implements Command {
	private String id,username,password,name;
	

	public UpdateDoctorCommand(String id, String username, String password, String name) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
	}


	@Override
	public Object execute() {
		Doctor doc= new Doctor();
		doc.setIdaccount(Integer.parseInt(id));
		doc.setUsername(username);
		doc.setPassword(password);
		doc.setName(name);
		try {
			DoctorMapper.getInstance().update(doc);
			return "accountUpdated";
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		
		return "accountNotUpdated";
	}

}

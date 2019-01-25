package commands;

import mappers.DataSourceException;
import mappers.DoctorMapper;
import model.Doctor;

public class CreateDoctorCommand implements Command {
	private String username, password, name;

	public CreateDoctorCommand(String username, String password, String name) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
	}

	@Override
	public Object execute() {

		try {
			Doctor doctor = new Doctor();
			doctor.setName(name);
			doctor.setUsername(username);
			doctor.setPassword(password);
			DoctorMapper.getInstance().create(doctor);

			return DoctorMapper.getInstance().getDoctor(username);
		} catch (DataSourceException e) {
			e.printStackTrace();
			return "NotCreated";
		}
	}
}

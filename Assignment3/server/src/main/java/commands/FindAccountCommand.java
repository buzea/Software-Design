package commands;

import mappers.DataSourceException;
import mappers.DoctorMapper;
import mappers.StaffMapper;
import model.Doctor;
import model.Staff;

public class FindAccountCommand implements Command {
	private String username;

	public FindAccountCommand(String username) {
		this.username = username;
	}

	@Override
	public Object execute() {
		try {
			Doctor doc = DoctorMapper.getInstance().getDoctor(username);
			if (doc != null) {
				return doc;
			}

		} catch (DataSourceException e) {
			e.printStackTrace();
		}

		try {
			Staff staff = StaffMapper.getInstance().getStaff(username);
			if (staff != null) {
				return staff;
			}
		} catch (DataSourceException e) {
			e.printStackTrace();
		}

		return "NotFound";
	}

}

package commands;

import mappers.DataSourceException;
import mappers.StaffMapper;
import model.Staff;

public class CreateStaffCommand implements Command {
	private String username, password, type;


	public CreateStaffCommand(String username, String password, String type) {
		super();
		this.username = username;
		this.password = password;
		this.type = type;
	}


	@Override
	public Object execute() {
		Staff staff = new Staff();
		staff.setPassword(password);
		staff.setUsername(username);
		if (type.equalsIgnoreCase("admin")) {
			staff.setRole(Staff.Type.ADMIN);
		} else {
			staff.setRole(Staff.Type.SECRETARY);
		}
		try {
			StaffMapper.getInstance().create(staff);
			return StaffMapper.getInstance().getStaff(username);
		} catch (DataSourceException e) {
			e.printStackTrace();
			return "NotCreated";
		}

	}

}

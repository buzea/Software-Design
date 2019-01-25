package commands;

import mappers.DataSourceException;
import mappers.DoctorMapper;

public class GetDoctorsCommand implements Command {

	@Override
	public Object execute() {
		try {
			return DoctorMapper.getInstance().getAll();
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return null;
	}

}

package commands;

import mappers.DataSourceException;
import mappers.PatientMapper;

public class GetPattientsCommand implements Command {

	@Override
	public Object execute() {
		try {
			return PatientMapper.getInstance().getAll();
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return null;
	}

}

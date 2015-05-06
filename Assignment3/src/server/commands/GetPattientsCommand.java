package server.commands;

import server.mappers.DataSourceException;
import server.mappers.PatientMapper;

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

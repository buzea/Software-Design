package commands;

import mappers.ConsultationMapper;
import mappers.DataSourceException;

public class DeleteConsultCommand implements Command {
	private String id;

	public DeleteConsultCommand(String id) {
		super();
		this.id = id;
	}

	@Override
	public Object execute() {
		try {
			ConsultationMapper.getInstance().delete(Integer.parseInt(id));
			return "ConsultDeleted";
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ConsultNotDeleted";
	}

}

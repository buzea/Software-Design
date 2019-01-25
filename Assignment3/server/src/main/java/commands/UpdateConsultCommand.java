package commands;

import mappers.ConsultationMapper;
import mappers.DataSourceException;
import model.Consultation;

public class UpdateConsultCommand implements Command {
	private String id, prescription, diagnosis, observations;

	public UpdateConsultCommand(String id, String prescription, String diagnosis, String observations) {
		super();
		this.id = id;
		this.prescription = prescription;
		this.diagnosis = diagnosis;
		this.observations = observations;
	}

	@Override
	public Object execute() {

		try {
			int idInt = Integer.parseInt(id);
			Consultation consultation = ConsultationMapper.getInstance().getConsultation(idInt);
			consultation.setDiagnosis(diagnosis);
			consultation.setObservations(observations);
			consultation.setPrescription(prescription);
			ConsultationMapper.getInstance().update(consultation);
		} catch (DataSourceException e) {
			e.printStackTrace();
		}

		return "Reload Consults";
	}

}

package model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the consultation database table.
 */
@Entity
@NamedQuery(name = "Consultation.findAll", query = "SELECT c FROM Consultation c")
public class Consultation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idconsultation;

	private String diagnosis;

	private String observations;

	private String prescription;

	private Timestamp time;

	//bi-directional many-to-one association to Doctor
	@ManyToOne
	@JoinColumn(name = "idDoctor")
	private model.Doctor doctor;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name = "idPatient")
	private model.Patient patient;

	public Consultation() {
	}

	public int getIdconsultation() {
		return this.idconsultation;
	}

	public void setIdconsultation(int idconsultation) {
		this.idconsultation = idconsultation;
	}

	public String getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getObservations() {
		return this.observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getPrescription() {
		return this.prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public model.Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(model.Doctor doctor) {
		this.doctor = doctor;
	}

	public model.Patient getPatient() {
		return this.patient;
	}

	public void setPatient(model.Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Consultation [idconsultation=" + idconsultation + ", diagnosis=" + diagnosis + ", observations="
				+ observations + ", prescription=" + prescription + ", time=" + time + ", doctor=" + doctor.getName()
				+ ", patient=" + patient.getName() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consultation other = (Consultation) obj;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (time == null) {
			return other.time == null;
		} else return time.equals(other.time);
	}
}
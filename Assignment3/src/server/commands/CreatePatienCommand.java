package server.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import client.model.Patient;
import server.mappers.PatientMapper;

public class CreatePatienCommand implements Command {
	@SuppressWarnings("unused")
	private String staffUsername, pncString, name, birthdate, address;
	private SimpleDateFormat df =new SimpleDateFormat("dd-MM-yyyy");

	
	public CreatePatienCommand(String staffUsername, String pncString, String name, String birthdate, String address) {
		super();
		this.staffUsername = staffUsername;
		this.pncString = pncString;
		this.name = name;
		this.birthdate = birthdate;
		this.address = address;
	}

	@Override
	public Object execute() {
		
		try{
		Patient patient = new Patient();
		int pnc=Integer.parseInt(pncString);
		patient.setPnc(pnc);
		patient.setName(name);
		Date date = df.parse(birthdate);
		patient.setBirtdate(date);
		patient.setAddress(address);
		PatientMapper.getInstance().create(patient);
		return "PatientCreated";
		
		}catch(Exception e){
			return "PatientNotCreated";
		}
		
		
	}

}

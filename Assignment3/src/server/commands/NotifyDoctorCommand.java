package server.commands;

import java.io.IOException;
import java.util.List;

import server.connections.MainServer;
import server.connections.ServerToClientConnection;
import server.mappers.ConsultationMapper;
import server.mappers.DataSourceException;
import client.listener.DoctorNotification;
import client.model.Consultation;

public class NotifyDoctorCommand implements Command {
	private String idConsult;
	

	public NotifyDoctorCommand(String idConsult) {
		super();
		this.idConsult = idConsult;
	}


	@Override
	public Object execute() {
		try {
			Consultation consult = ConsultationMapper.getInstance().getConsultation(Integer.parseInt(idConsult));
			String username=consult.getDoctor().getUsername();
			List<ServerToClientConnection> connections = MainServer.getInstance().getConnections();
			//System.out.println(connections);
			for(ServerToClientConnection c:connections){
				c.sendObj(new DoctorNotification(username));
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

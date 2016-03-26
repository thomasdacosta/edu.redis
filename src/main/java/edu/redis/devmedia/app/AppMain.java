package edu.redis.devmedia.app;

import edu.redis.devmedia.dao.OperationsDAO;

public class AppMain {
	
	public static void main(String[] args) {
		OperationsDAO basicOperationsDAO = new OperationsDAO();
		basicOperationsDAO.clear();
		basicOperationsDAO.basicOperations();
		basicOperationsDAO.serializeDeserializeOperations();
		basicOperationsDAO.hashesOperations();
		basicOperationsDAO.listOperations();
	}

}

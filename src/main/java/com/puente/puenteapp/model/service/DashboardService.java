package com.puente.puenteapp.model.service;

import java.util.Date;

import com.puente.puenteapp.model.dto.DashboardRequest;
import com.puente.puenteapp.model.dto.DashboardStats;

public interface DashboardService {
	
	DashboardStats getUserStats(Date dateInitial, Date dateFinal);
}

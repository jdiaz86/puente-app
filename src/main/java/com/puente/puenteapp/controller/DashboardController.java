package com.puente.puenteapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.dto.DashboardStats;
import com.puente.puenteapp.model.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {
	
	@Autowired
    private DashboardService service;

	@RequestMapping(value = "/userStats")
    public DashboardStats getUserStats(@RequestParam(value = "dateInitial") Date dateInitial,
    									@RequestParam(value = "dateFinal") Date dateFinal) throws PuenteException {
        return service.getUserStats(dateInitial, dateFinal);
    }
	
}

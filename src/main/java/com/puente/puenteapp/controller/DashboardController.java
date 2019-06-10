package com.puente.puenteapp.controller;

import com.puente.puenteapp.model.dto.DashboardStats;
import com.puente.puenteapp.model.dto.GraphDto;
import com.puente.puenteapp.model.service.DashboardService;
import com.puente.puenteapp.util.ExportViewCreator;
import com.puente.puenteapp.util.PuenteException;
import com.puente.puenteapp.view.pdf.GraphPdfView;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {
    
    @Autowired
    private DashboardService service;
    @Autowired
    private ExportViewCreator exportViewCreator;
    
    @RequestMapping(value = "/userStats")
    public DashboardStats getUserStats(@RequestParam(value = "dateInitial") Date dateInitial,
            @RequestParam(value = "dateFinal") Date dateFinal) throws PuenteException {
        
        return service.getUserStats(dateInitial, dateFinal);
    }
    
    @RequestMapping(value = "/exportXls", method = POST)
    public ModelAndView generateExcelStudy(@RequestBody GraphDto dto, HttpServletRequest request) {
        Enumeration<String> enumeration = request.getSession().getAttributeNames();
        while (enumeration.hasMoreElements()) {
            System.out.println("Atribute: " + enumeration.nextElement());
        }
        Map<String, Object> params = addExportParams(dto);
        GraphPdfView view = exportViewCreator.getGraphPdfView();
        view.setScreenTitle(dto.getTitle());
        return new ModelAndView(view, params);
    }
    
    private Map<String, Object> addExportParams(GraphDto dto) {
        Map<String, Object> params = new HashMap<>();
        params.put(GraphDto.class.getSimpleName(), dto);
        params.put("username", dto.getUsername());
        params.put("time", dto.getTime());
        params.put("sponsor", dto.getSponsor());
        return params;
    }
    
}

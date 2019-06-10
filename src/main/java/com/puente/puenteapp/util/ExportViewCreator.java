/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puente.puenteapp.util;

import com.puente.puenteapp.view.pdf.GraphPdfView;
import javax.inject.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jdiaz
 */
@Component
public class ExportViewCreator {
    
    @Autowired
    private Provider<GraphPdfView> provider;
    
    public GraphPdfView getGraphPdfView() {
        return provider.get();
    }
    
}

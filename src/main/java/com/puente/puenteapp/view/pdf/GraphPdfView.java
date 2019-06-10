package com.puente.puenteapp.view.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.puente.puenteapp.model.dto.GraphDto;
import com.puente.puenteapp.model.dto.Series;
import static com.puente.puenteapp.util.ConstUtil.EMPTY_STRING;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Yauheni Zubovich
 */
@Component
@Scope(value = "request")
public class GraphPdfView extends AbstractPdfView {
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GraphDto view = (GraphDto) model.get(GraphDto.class.getSimpleName());
        buildHeader(model, document, writer, response, view.getTitle(), null);
        generateReport(model, writer, document, view);
    }
    
    @Override
    protected void generateReport(Map<String, Object> model, PdfWriter writer, Document document, Serializable object) throws DocumentException {
        GraphDto view = (GraphDto) object;
        List<Series> tableList = view.getSeries();
        int rows = tableList.get(0).getData().size();
        List<String> cols = view.getCols();
        
        relativeWidths = new float[]{ 1, 2, 2 };
        alignItems = new int[]{ LEFT, LEFT, LEFT };
        List<String> titlesList = new ArrayList<>();
        titlesList.add(EMPTY_STRING);
        tableList.forEach((dto) -> {
            titlesList.add(dto.getName());
        });
        tableTitles = titlesList.stream().toArray(String[]::new);
        tableBody = new String[rows + 1][];
        int i = 0;
        Number col1 = 0;
        Number col2 = 0;
        for (int x = 0; x < rows ; x++) {
            col1 = addNumbers(col1, tableList.get(0).getData().get(x));
            col2 = addNumbers(col2, tableList.get(1).getData().get(x));
            tableBody[i++] = new String[]{
                cols.get(x),
                tableList.get(0).getData().get(x) + "",
                tableList.get(1).getData().get(x) + ""
            };
        }
        tableBody[i++] = new String[]{
            "Total",
            col1 + "",
            col2 + ""
        };
        rownum = insertTable(document, writer, rownum, EMPTY_STRING);
    }
    
    
}

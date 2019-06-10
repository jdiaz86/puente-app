package com.puente.puenteapp.view.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.puente.puenteapp.util.ConstUtil.EMPTY_STRING;
import static com.puente.puenteapp.util.ConstUtil.JSON_FORMAT_DATE;
import com.puente.puenteapp.view.AbstractExportView;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jorge Diaz
 */
public abstract class AbstractPdfView extends AbstractExportView {

    protected int rownum = 7;
    static final int LEFT = Element.ALIGN_LEFT;
    static final int RIGHT = Element.ALIGN_RIGHT;
    static final int MIDDLE = Element.ALIGN_MIDDLE;
    static final Font NORMAL_TITLE = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
    static final Font NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
    static final String INIT_BOX = "art";
    static final int LINE = 20;
    static final Rectangle BOX = new Rectangle(30, 54, 559, 788);
    float[] relativeWidths;
    int[] alignItems;
    private static final String CONTENT_TYPE = "application/pdf";

    public AbstractPdfView() {
        setContentType(CONTENT_TYPE);
    }

    protected PdfHeader buildHeader(Map<String, Object> model, Document document, PdfWriter writer, HttpServletResponse response, String title, Serializable item) throws DocumentException {
        username = ANONYMOUS_USER;
        //if no time comes from UI, show server time
        time = JSON_FORMAT_DATE.format(new Date());
        if (model.containsKey(USER_NAME)) {
            username = (String) model.get(USER_NAME);
        }
        String sponsor = (String) model.get(SPONSOR);
        PdfHeader header = new PdfHeader(title, username, sponsor, item);
        writer.setBoxSize(INIT_BOX, BOX);
        writer.setPageEvent(header);
        document.open();
        document.add(new Chunk(EMPTY_STRING));
        document.addTitle(title);
        return header;
    }

    private float getLeft(int offset) {
        return BOX.getLeft() + offset;
    }

    private float getTop(int offset) {
        return BOX.getTop() - offset;
    }

    private void insertCell(PdfPTable table, String text, Font font, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(align);
        table.addCell(cell);
    }

    /**
     * Insert a table to display data in a tabular way
     */
    int insertTable(Document document, PdfWriter writer, int rownumTable, String title) {
        rownumTable++;
        PdfPTable table = insertHeaderTable(title);
        int rowsOnPage = 0;
        float currentPosition = BOX.getBottom() + rownumTable * LINE;
        float maxRowHeight = 0;
        for (String[] itm : tableBody) {
            if (currentPosition + maxRowHeight > BOX.getTop()) {
                table.writeSelectedRows(0, -1, getLeft(0), getTop(LINE * rownumTable), writer.getDirectContent());
                document.newPage();
                rownumTable = 7;
                table = insertHeaderTable(title);
                rowsOnPage = 0;
                currentPosition = 200;
            }
            for (int j = 0; j < itm.length; j++) {
                if ( j == 0 || j == itm.length) {
                    insertCell(table, itm[j], NORMAL_TITLE, alignItems[j]);                    
                } else {
                    insertCell(table, itm[j], NORMAL, alignItems[j]);
                }
            }
            table.completeRow();
            rowsOnPage++;
            float rowHeight = table.getRowHeight(rowsOnPage);
            currentPosition += rowHeight;
            if (rowHeight > maxRowHeight) {
                maxRowHeight = rowHeight;
            }
        }
        checkForData(table);
        table.writeSelectedRows(0, -1, getLeft(0), getTop(LINE * rownumTable), writer.getDirectContent());
        return (int) (currentPosition / LINE);
    }

    /**
     * Insert the header section of a table
     */
    private PdfPTable insertHeaderTable(String title) {
        PdfPTable table = new PdfPTable(relativeWidths);
        table.setTotalWidth(530);
        table.getDefaultCell().setPadding(5);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        if (null != title) {
            addTitle(table, title);
        }
        if (tableTitles != null) {
            for (String tableTitle : tableTitles) {
                insertCell(table, tableTitle, NORMAL_TITLE, LEFT);
            }
        }
        table.completeRow();
        table.setHeaderRows(1);
        return table;
    }

    private void checkForData(PdfPTable table) {
        if (tableBody.length == 0) {
            completeEmptyRow(table);
        }
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // IE workaround: write into byte array first.
            ByteArrayOutputStream baos = createTemporaryOutputStream();
            // Apply preferences and build metadata.
            Document document = newDocument();
            PdfWriter writer = newWriter(document, baos);
            prepareWriter(writer);
            // Build PDF document.
            document.open();
            buildPdfDocument(model, document, writer, request, response);
            document.addCreationDate();
            document.addAuthor((String) model.get(SPONSOR));
            document.close();
            // Flush to HTTP response.
            writeToResponse(response, baos);
        } catch (Exception e) {
            //throw new PuenteException(e.getMessage());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private Document newDocument() {
        return new Document(PageSize.A4, 36, 36, 54, 36);
    }

    private PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }

    private void prepareWriter(PdfWriter writer) throws DocumentException {
        writer.setViewerPreferences(getViewerPreferences());
    }

    private int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    /**
     * Builds the pdf document for each report
     *
     * @param model
     * @param document
     * @param writer
     * @param request
     * @param response
     * @throws java.lang.Exception
     */
    protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Generates the body of the report
     *
     * @param model
     * @param writer
     * @param document
     * @param object
     * @throws DocumentException
     */
    protected abstract void generateReport(Map<String, Object> model, PdfWriter writer, Document document, Serializable object) throws DocumentException;

    private void completeEmptyRow(PdfPTable table) {
        PdfPCell cell = new PdfPCell(new Phrase(NO_DATA_FOUND, NORMAL));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(tableTitles.length);
        table.addCell(cell);
        table.completeRow();
    }

    private void addTitle(PdfPTable table, String title) {
        PdfPCell cell = new PdfPCell(new Phrase(title, NORMAL_TITLE));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(tableTitles.length);
        table.addCell(cell);
        table.completeRow();
    }
    

}

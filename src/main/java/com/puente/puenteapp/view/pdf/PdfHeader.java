package com.puente.puenteapp.view.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import static com.puente.puenteapp.util.ConstUtil.JSON_FORMAT_DATE;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jorge Diaz
 */
class PdfHeader extends PdfPageEventHelper {

    private static final int LINE = 20;
    private static final String INIT_BOX = "art";
    protected static final Font MAIN_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD | Font.UNDERLINE);
    protected static final Font SUB_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
    protected static final Font NORMAL_TITLE = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
    protected static final Font NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
    protected static final int LEFT = Element.ALIGN_LEFT;
    protected static final int RIGHT = Element.ALIGN_RIGHT;
    protected static final int MIDDLE = Element.ALIGN_MIDDLE;
    private final String header;
    private final String userName;
    private final Serializable item;
    private PdfTemplate total;
    private final String title;

    public PdfHeader(String header, String userName, String title, Serializable item) {
        this.header = header;
        this.userName = userName;
        this.item = item;
        this.title = title;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        super.onOpenDocument(writer, document);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        if ((writer.getPageNumber() - 1) == 1) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }
        Rectangle box = writer.getBoxSize(INIT_BOX);
        generateHeader(writer, box);
        //SUB-TITLE
        placeTextAt(writer, Element.ALIGN_CENTER, new Phrase(header, SUB_TITLE), (box.getLeft() + box.getRight()) / 2, box.getTop() - LINE * 5);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1), NORMAL), 7, 2, 0);
    }

    public float getLeft(Rectangle box, int offset) {
        return box.getLeft() + offset;
    }

    public float getTop(Rectangle box, int offset) {
        return box.getTop() - offset;
    }

    public float getRight(Rectangle box, int offset) {
        return box.getLeft() - offset;
    }

    protected float getMiddle(Rectangle box, int offset) {
        return ((box.getRight() + box.getLeft()) / 2) - offset;
    }

    public void placeTextAt(PdfWriter writer, int align, Phrase phrase, float px, float py) {
        ColumnText.showTextAligned(writer.getDirectContent(), align, phrase, px, py, 0);
    }

    public void placePhraseAt(PdfWriter writer, String phraseOne, String phraseTwo, int align, float px, float py) {
        Phrase p = new Phrase();
        p.add(new Phrase(phraseOne, NORMAL_TITLE));
        p.add(new Phrase(phraseTwo, NORMAL));
        placeTextAt(writer, align, p, px, py);
    }

    private void generateHeader(PdfWriter writer, Rectangle box) {
        placeTextAt(writer, Element.ALIGN_CENTER, new Phrase(title, MAIN_TITLE), (box.getLeft() + box.getRight()) / 2, box.getTop());
        placePhraseAt(writer, "Usuario: ", userName, LEFT, getLeft(box, 0), getTop(box, LINE * 2));
        placePhraseAt(writer, "Fecha: ", JSON_FORMAT_DATE.format(new Date()), RIGHT, box.getRight(), box.getTop() - LINE * 2);
        placePhraseAt(writer, "Página: ", (writer.getPageNumber() - 1) + " of ", LEFT, getLeft(box, 0), getTop(box, LINE * 3));
        PdfPTable table = new PdfPTable(1);
        try {
            table.setWidths(new int[]{2});
            table.setTotalWidth(20);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(10);
            PdfPCell cell = new PdfPCell(Image.getInstance(total));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            table.writeSelectedRows(0, -1, getLeft(box, 47), getTop(box, 46), writer.getDirectContent());
        } catch (DocumentException ex) {
        }
    }
}

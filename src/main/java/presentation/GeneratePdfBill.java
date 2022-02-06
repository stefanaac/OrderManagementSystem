package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Order;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Clasa care genereaza chitanta unei comenzi intr-un fisier pdf.
 */
public class GeneratePdfBill {
    public Document clientDocument;
    public Document productDocument;
    public Document orderDocument;
    public Document document;
    Order o;

    /**
     * Creeaza un nou pdf
     */
    public GeneratePdfBill() throws FileNotFoundException, DocumentException {

    }

    /**
     * Scrie string-ul "s" dat ca parametru in pdf
     */
    public void writePdf(Order o) throws DocumentException, FileNotFoundException {
        this.document = new Document();
        PdfWriter.getInstance(this.document, new FileOutputStream("C:\\Users\\Stefana\\IdeaProjects\\PT2021_30221_Chelemen_Stefana_Assignment_3\\src\\myBill.pdf "));
        this.document.open();
        StringBuilder sb = new StringBuilder();
        Float f = o.getQuantity() * o.getTotal();
        sb.append("Your BILL:\n" + "Nume client:........................... " + o.getClient() + "\n" + "Nume produs:........................... " + o.getProduct() + "\n" + "Total:................................. " + f.toString() + "\n");
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.DARK_GRAY);
        Chunk chunk = new Chunk(sb.toString(), font);
        this.document.add(new Paragraph(chunk));
        closePdf(this.document);
    }

    /**
     * Inchide pdf-ul
     */
    public void closePdf(Document document) {
        document.close();
    }
}

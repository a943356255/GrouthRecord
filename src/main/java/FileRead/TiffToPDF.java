package FileRead;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;

import java.io.FileOutputStream;

public class TiffToPDF {

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Usage: Tiff2Pdf file1.tif [file2.tif  fileN.tif]");
//            System.exit(1);
//        }

        String tiff = "D:\\git\\test.tif", pdf;
        for (int i = 0; i < args.length; i++) {
            pdf = tiff.substring(0, tiff.lastIndexOf('.') + 1) + "pdf";
            Document document = new Document(PageSize.LETTER, 0, 0, 0, 0);
            int pages = 0, comps = 0;
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdf));
                document.open();
                PdfContentByte cb = writer.getDirectContent();
                RandomAccessFileOrArray ra = null;
                try {
                    ra = new RandomAccessFileOrArray(tiff);
                    comps = TiffImage.getNumberOfPages(ra);
                } catch (Throwable e) {
                    System.out.println("Exception in " + tiff + " " + e.getMessage());
                    continue;
                }

                System.out.println("Processing: " + tiff);
                for (int c = 0; c < comps; ++c) {
                    try {
                        Image img = TiffImage.getTiffImage(ra, c + 1);
                        if (img != null) {
                            System.out.println("page " + (c + 1));
                            img.scalePercent(7200f / img.getDpiX(), 7200f / img.getDpiY());
                            document.setPageSize(new Rectangle(img.getScaledWidth(), img.getScaledHeight()));
                            img.setAbsolutePosition(0, 0);
                            cb.addImage(img);
                            document.newPage();
                            ++pages;
                        }
                    } catch (Throwable e) {
                        System.out.println("Exception " + tiff + " page " + (c + 1) + " " + e.getMessage());
                    }
                }
                ra.close();
                document.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            System.out.println("done...");
        }
    }

}

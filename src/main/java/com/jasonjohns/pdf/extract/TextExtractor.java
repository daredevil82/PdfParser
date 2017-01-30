package com.jasonjohns.pdf.extract;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by jasonjohns on 1/18/17.
 */
public class TextExtractor extends PDFTextStripper {

    private Logger logger = null;


    public TextExtractor() throws IOException {
        logger = LoggerFactory.getLogger(TextExtractor.class);
    }

    public void extractText(PDDocument document) throws IOException {
        this.setSortByPosition(true);
        this.setStartPage(0);
        this.setEndPage(document.getNumberOfPages());

        try {
            logger.info("Extracting text from [{}] pages", document.getNumberOfPages());
            Writer writer = new OutputStreamWriter(new ByteArrayOutputStream());
            this.writeText(document, writer);

        }
        finally {
            document.close();
        }

    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
        StringBuilder builder = new StringBuilder("");

        logger.info("Processing string [{}]", string);

        for (TextPosition text: textPositions)
        {
//            System.out.println( "String[" + text.getXDirAdj() + "," +
//                    	                    text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
//                    	                    text.getXScale() + " height=" + text.getHeightDir() + " space=" +
//                    	                    text.getWidthOfSpace() + " width=" +
//                    	                    text.getWidthDirAdj() + "]" + text.getUnicode() );
            builder.append(text.getUnicode());
        }

    }

}

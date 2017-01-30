package com.jasonjohns.pdf;

import com.jasonjohns.pdf.extract.TextExtractor;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;


import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;


public class PdfParser
{
    private static String RESOURCE_URL = "http://s3-us-west-2.amazonaws.com";
    private static String DOCUMENT_BUCKET = "degreedata-pdf";

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("h", "help", true, "PdfParser usage information");
        options.addOption("u", "url", true, "URL to PDF document");
        return options;
    }

    private static void usage(PrintStream out, String[] args) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("PdfParser", buildOptions());
        out.println("Your arguments were: \n" + StringUtils.join(args, ' '));
    }

    private static CommandLine getCommandLine(PrintStream out, String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cl;
        try {
            cl = parser.parse(buildOptions(), args);
        }
        catch (ParseException e) {
            usage(out, args);
            return null;
        }

        if (cl.getArgList().size() != 0) {
            usage(out, args);
            return null;
        }

        return cl;
    }

    public static void main( String[] args ) throws IOException
    {
        try
        {
            String catalogId = args[0];
            URL catalogUrl = new URL(String.format("%s/%s/%s.pdf", RESOURCE_URL, DOCUMENT_BUCKET, catalogId));
            PDDocument document = PDDocument.load(catalogUrl.openStream());

            TextExtractor extractor = new TextExtractor();
            extractor.extractText(document);

        }
        catch (IndexOutOfBoundsException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
}

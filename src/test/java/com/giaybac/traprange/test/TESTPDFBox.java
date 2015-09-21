/**
* Copyright (C) 2015, GIAYBAC
*
* Released under the MIT license
*/
package com.giaybac.traprange.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;
import org.junit.Test;

import com.giaybac.traprange.TrapRangeBuilder;
import com.google.common.collect.Range;

/**
 *
 * @author THOQ LUONG Mar 21, 2015 11:23:40 PM
 */
public class TESTPDFBox extends PDFTextStripper {

    //--------------------------------------------------------------------------
    //  Members
    private final List<Range<Integer>> ranges = new ArrayList<>();

    private TrapRangeBuilder trapRangeBuilder = new TrapRangeBuilder();

    //--------------------------------------------------------------------------
    //  Initialization and releasation
    public TESTPDFBox() throws IOException {
        super.setSortByPosition(true);
    }

    //--------------------------------------------------------------------------
    //  Getter N Setter
    //--------------------------------------------------------------------------
    //  Method binding
    @Test
    public void test() throws IOException {
        /*String filePath = "D:\\traprange\\_Docs\\TK0976-AB5-0-2014042211.pdf";
        //String filePath = "C:\\Users\\ThoLuong\\Downloads\\Download\\1986 NL Batting - Sheet1.pdf";
        File pdfFile = new File(filePath);*/
        PDDocument pdDocument = PDDocument.load(this.getClass().getResourceAsStream("/sample-1.pdf"));
        //PrintTextLocations printer = new PrinTextLocations();
        List pages = pdDocument.getDocumentCatalog().getAllPages();
        PDPage page = (PDPage) pages.get(0);
        PDStream stream = page.getContents();

        this.processStream(page, page.findResources(), stream.getStream());
        //Print out all text
        Collections.sort(ranges, new Comparator<Range>() {
            @Override
            public int compare(Range o1, Range o2) {
                return o1.lowerEndpoint().compareTo(o2.lowerEndpoint());
            }
        });
        for (Range range : ranges) {
            System.out.println("> " + range);
        }
        //Print out all ranges
        List<Range<Integer>> trapRanges = trapRangeBuilder.build();
        for (Range trapRange : trapRanges) {
            System.out.println("TrapRange: " + trapRange);
        }
    }

    //--------------------------------------------------------------------------
    //  Implement N Override
    @Override
    protected void processTextPosition(TextPosition text) {
        Range range = Range.closed((int) text.getY(), (int) (text.getY() + text.getHeight()));
        System.out.println("Text: " + text.getCharacter());
        trapRangeBuilder.addRange(range);
    }
    //--------------------------------------------------------------------------
    //  Utils
    //--------------------------------------------------------------------------
    //  Inner class
}

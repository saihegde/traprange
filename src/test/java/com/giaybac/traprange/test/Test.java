package com.giaybac.traprange.test;

import com.giaybac.traprange.PDFTableExtractor;
import com.giaybac.traprange.entity.Table;
import com.giaybac.traprange.entity.TableRow;

import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		try{
			PDFTableExtractor extractor = new PDFTableExtractor();
			List<Table> tables = extractor.setSource(Test.class.getResourceAsStream("/sample-1.pdf"))
					.addPage(0)
					.exceptLine(new int[]{0, 1, -1}) //the first line in each page
					.extract();
			System.out.println(tables.get(0).toString());
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
